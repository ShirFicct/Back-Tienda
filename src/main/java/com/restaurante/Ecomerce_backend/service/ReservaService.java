package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ReservaDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.InventarioRepository;
import com.restaurante.Ecomerce_backend.repositorios.ReservaRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private DetalleReservaService detalleReservaService;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private Met_pagoService met_pagoService;


    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }

    @Transactional
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ReservaDTO no puede ser nulo");
        }

        // Validar abono (50%) vs total
        if (reservaDTO.getAbono() <= 0 || reservaDTO.getAbono() >= reservaDTO.getTotal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El abono debe ser > 0 y < al total de la reserva");
        }//

        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
        Met_Pago metPagoEncontrado = met_pagoService.obtMetoPorId(reservaDTO.getMetPagoId());
        // Construir la reserva
        Reserva reserva = new Reserva();
        reserva.setFecha_creacion(reservaDTO.getFecha_creacion());
        reserva.setFecha_expiracion(reservaDTO.getFecha_expiracion());

        // Estado inicial: "pagado_parcial" (puesto que ya pagó el 50%)
        reserva.setEstado("pagado_parcial");
        reserva.setUsuario(usuario);
        reserva.setTotal(reservaDTO.getTotal());
        reserva.setAbono(reservaDTO.getAbono());
        // Si tienes un campo metPago, podrías setearlo aquí:
        reserva.setMet_Pago(metPagoEncontrado); // según tu repositorio

        // Guardar en la base de datos
        return reservaRepository.save(reserva);
    }

    /**
     * Completar el pago de la reserva antes de la fecha de expiración:
     * Se paga el restante para alcanzar el 100%.
     */
    @Transactional
    public Reserva completarPagoReserva(Long reservaId, float montoAdicional) {
        Reserva reserva = obtenerReservaPorId(reservaId);

        // Verificar si está cancelada
        if ("cancelada".equals(reserva.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva ya está cancelada");
        }
        // Verificar si expiró
        if (new Date().after(reserva.getFecha_expiracion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva ha expirado");
        }

        // Sumar el nuevo abono
        float nuevoAbono = reserva.getAbono() + montoAdicional;
        if (nuevoAbono < reserva.getTotal()) {
            // Sigue pago parcial
            reserva.setAbono(nuevoAbono);
            reserva.setEstado("pagado_parcial");
            return reservaRepository.save(reserva);
        }

        // Si alcanza o supera el total, ya pagó 100%
        reserva.setAbono(reserva.getTotal());
        reserva.setEstado("finalizada"); // completó el pago total

        // **Aquí convertimos la reserva en pedido**:
        Pedido pedidoCreado = pedidoService.crearPedidoDesdeReserva(reserva);
        // (Opcional) Guardamos una relación en la reserva, si la tienes:
        reserva.setPedido(pedidoCreado);

        return reservaRepository.save(reserva);
    }

    /**
     * Cancelar reservas que hayan expirado sin completar el pago
     */
    @Transactional
    public void cancelarReservasExpiradas() {
        List<Reserva> reservas = reservaRepository.findAll();
        Date hoy = new Date();
        for (Reserva r : reservas) {
            if ("pagado_parcial".equals(r.getEstado())
                    && hoy.after(r.getFecha_expiracion())) {
                // Marcar la reserva como cancelada
                r.setEstado("cancelada");
                reservaRepository.save(r);

                // **Devolver stock** de cada detalle:
                // 1. Obtener detalles de la reserva
                List<Detalle_reseva> detalles = detalleReservaService.findByReservaId(r.getId());
                // 2. Para cada detalle, devolver stock
                for (Detalle_reseva dr : detalles) {
                    Inventario inv = inventarioRepository.findByProductoIdAndSucursalIdAndTallaIdAndColorId(
                                    dr.getProducto().getId(),
                                    dr.getSucursal().getId(),
                                    dr.getTalla().getId(),
                                    dr.getColor().getId()
                            )
                            .orElse(null);

                    if (inv != null) {
                        inv.setStock(inv.getStock() + dr.getCantidad());
                        inventarioRepository.save(inv);
                    }
                }
            }
        }
    }

    /**
     * Modificar campos de la reserva (fechas, usuario, etc.)
     * Ajusta los nombres para que coincidan con tu ReservaDTO
     */
    @Transactional
    public Reserva modificarReserva(Long id, ReservaDTO reservaDTO) {
        Reserva reservaExistente = obtenerReservaPorId(id);

        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Ajustar los nombres a los que tengas en ReservaDTO
        reservaExistente.setFecha_creacion(reservaDTO.getFecha_creacion());
        reservaExistente.setFecha_expiracion(reservaDTO.getFecha_expiracion());
        reservaExistente.setUsuario(usuario);

        // Manejo del estado: si tu DTO lo maneja como boolean, conviene cambiarlo a String
        // Podrías tener: if (reservaDTO.isActivo()) { reservaExistente.setEstado("pagado_parcial"); } ...
        // O si guarda un string directo, haz:
        reservaExistente.setEstado(reservaDTO.getEstado());
        return reservaRepository.save(reservaExistente);
    }


    @Transactional
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setEstado("eliminada");
        reservaRepository.save(reserva);
    }
}
