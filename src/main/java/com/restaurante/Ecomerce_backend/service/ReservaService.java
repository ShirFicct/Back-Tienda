package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ReservaDTO;
import com.restaurante.Ecomerce_backend.model.Reserva;
import com.restaurante.Ecomerce_backend.model.Usuario;
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

    // Listar todas las reservas
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    // Obtener una reserva por ID
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }

    public Reserva guardarCambios(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Crear una nueva reserva
    @Transactional
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ReservaDTO no puede ser nulo");
        }

        // Validar abono vs total
        if (reservaDTO.getAbono() <= 0 || reservaDTO.getAbono() >= reservaDTO.getTotal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El abono debe ser mayor que 0 y menor al total de la reserva");
        }

        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        // Construir la reserva
        Reserva reserva = new Reserva();
        reserva.setFecha_creacion(reservaDTO.getFecha_creacion());
        reserva.setFecha_expiracion(reservaDTO.getFecha_expiracion());
        reserva.setEstado("pagado_parcial"); // Asumimos que paga el 50% al crear la reserva
        reserva.setUsuario(usuario);
        reserva.setTotal(reservaDTO.getTotal());
        reserva.setAbono(reservaDTO.getAbono()); // 50% del total
        // Si tienes un Met_PagoRepository, podrías buscarlo y setearlo:
        // reserva.setMet_Pago(metPagoEncontrado);

        // Guardar en la BD
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva completarPagoReserva(Long reservaId, float montoAdicional) {
        Reserva reserva = obtenerReservaPorId(reservaId);

        // Verificar si ya está cancelada o expirada
        if (reserva.getEstado().equals("cancelada")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva ya está cancelada");
        }
        if (new Date().after(reserva.getFecha_expiracion())) {
            // Si la fecha actual es posterior a la fecha de expiración, no se puede completar
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva ha expirado");
        }

        // Sumar el nuevo abono
        float nuevoAbono = reserva.getAbono() + montoAdicional;

        if (nuevoAbono < reserva.getTotal()) {
            // Aún no completa el 100%
            reserva.setAbono(nuevoAbono);
            // Podrías manejar un estado "pagado_parcial" todavía
            reserva.setEstado("pagado_parcial");
            return reservaRepository.save(reserva);
        }

        // Si alcanza o supera el total
        reserva.setAbono(reserva.getTotal());
        reserva.setEstado("finalizada"); // Se completó el pago

        // (OPCIONAL) Crear Pedido a partir de la Reserva
        /*
        Pedido nuevoPedido = pedidoService.crearPedidoDesdeReserva(reserva);
        reserva.setPedido(nuevoPedido);
        */

        return reservaRepository.save(reserva);
    }

    @Transactional
    public void cancelarReservasExpiradas() {
        List<Reserva> reservas = reservaRepository.findAll();
        Date hoy = new Date();
        for (Reserva r : reservas) {
            if (r.getEstado().equals("pagado_parcial") && hoy.after(r.getFecha_expiracion())) {
                // Marcar como cancelada
                r.setEstado("cancelada");
                // Podrías devolver stock de los productos en DetalleReserva
                // (ver DetalleReservaService)
                reservaRepository.save(r);
            }
        }
    }
    // Modificar una reserva
    public Reserva modificarReserva(Long id, ReservaDTO reservaDTO) {
        Reserva reservaExistente = obtenerReservaPorId(id);
        if (reservaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        }

        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        reservaExistente.setFecha(reservaDTO.getFecha());
        reservaExistente.setEstado(reservaDTO.isEstado());
        reservaExistente.setUsuario(usuario);

        return reservaRepository.save(reservaExistente);
    }

    // Eliminar una reserva
    @Transactional
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);
        // Si prefieres borrado lógico, haz:
        // reserva.setEstado("cancelada");
        // reservaRepository.save(reserva);
        reservaRepository.delete(reserva);
    }
}
