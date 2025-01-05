package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.DetallePedidoDTO;
import com.restaurante.Ecomerce_backend.dto.PedidoDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private Met_pagoService met_pagoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private DetallePedRepository detallePedRepository;

    public List<Pedido> listPedido() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listByUser(Long idUsuario) {
        Usuario user = obtenerUsuarioPorId(idUsuario);
        return pedidoRepository.findPedidosByUserIdByUserId(user.getId());
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        validarPedidoDTO(pedidoDTO);

        Usuario user = usuarioService.obtenerUserPorId(pedidoDTO.getUsuarioId());
        Met_Pago metPago = met_pagoService.obtMetoPorId(pedidoDTO.getIdMetPago());

        Pedido pedido = inicializarPedido(pedidoDTO, user, metPago);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        float montoTotal = procesarDetallesPedido(pedidoDTO.getDetalle(), pedidoGuardado, user);
        pedidoGuardado.setMonto_total(aplicarDescuentoPorSuscripcion(montoTotal, user));
        return pedidoRepository.save(pedidoGuardado);
    }

    public Pedido modificarPedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedidoExistente = obtenerPedidoPorId(id);
        validarPedidoDTO(pedidoDTO);

        Usuario user = usuarioService.obtenerUserPorId(pedidoDTO.getUsuarioId());
        Met_Pago metPago = met_pagoService.obtMetoPorId(pedidoDTO.getIdMetPago());

        actualizarPedido(pedidoExistente, pedidoDTO, user, metPago);

        float montoTotal = procesarDetallesPedido(pedidoDTO.getDetalle(), pedidoExistente, user);
        pedidoExistente.setMonto_total(aplicarDescuentoPorSuscripcion(montoTotal, user));
        return pedidoRepository.save(pedidoExistente);
    }

    public void eliminarPedido(Long id) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedido.setEstado("cancelado");
        pedidoRepository.save(pedido);
    }

    // Métodos auxiliares
    private Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioService.obtenerUserPorId(idUsuario);
    }

    private void validarPedidoDTO(PedidoDTO pedidoDTO) {
        if (pedidoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido no puede ser nulo");
        }
    }

    private Pedido inicializarPedido(PedidoDTO pedidoDTO, Usuario user, Met_Pago metPago) {
        Pedido pedido = new Pedido();
        pedido.setFecha(pedidoDTO.getFecha());
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setUsuario(user);
        pedido.setMet_Pago(metPago);
        return pedido;
    }

    private void actualizarPedido(Pedido pedido, PedidoDTO pedidoDTO, Usuario user, Met_Pago metPago) {
        pedido.setUsuario(user);
        pedido.setMet_Pago(metPago);
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setFecha(pedidoDTO.getFecha());
    }

    private float procesarDetallesPedido(List<Detalle_Pedido> detalles, Pedido pedido, Usuario user) {
        float montoTotal = 0f;

        for (Detalle_Pedido detalle : detalles) {
            Producto prod = detalle.getProducto();
            if (prod == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado");
            }

            // Llama a crearDetallePedido para procesar el detalle
            detalle = crearDetallePedido(detalle, pedido, prod, user);

            // Suma el subtotal del detalle al monto total
            montoTotal += detalle.getSubtotal();

            // Guarda el detalle en la base de datos
            detallePedRepository.save(detalle);
        }

        return montoTotal;
    }


    @Transactional
    public Pedido confirmarPedido(Long pedidoId) {
        Pedido pedido = obtenerPedidoPorId(pedidoId);

        if (!pedido.getEstado().equals("pendiente")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido ya fue procesado");
        }

        for (Detalle_Pedido detalle : pedido.getDetalle()) {
            Inventario inventario = detalle.getInventario();
            if (inventario.getStock() < detalle.getCantidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto: " + inventario.getProducto().getNombre());
            }

            // Actualizar stock
            inventario.setStock(inventario.getStock() - detalle.getCantidad());
            inventarioRepository.save(inventario);
        }
        return pedido;
    }


    private Detalle_Pedido crearDetallePedido(Detalle_Pedido detalle, Pedido pedido, Producto prod, Usuario user) {
        detalle.setPedido(pedido);  // Asocia el detalle al pedido correspondiente
        detalle.setProducto(prod);  // Asocia el detalle al producto correspondiente

        // Calcula el subtotal del detalle
        float subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();

        // Aplica promociones si corresponde
        Promocion promocion = prod.getPromocion();
        if (promocion != null && promocion.isActivo() &&
                (promocion.getSuscripcion().getNombre().equals(user.getSuscripcion().getNombre()) ||
                        promocion.getSuscripcion().getNombre().equals("STANDAR"))) {
            subtotal -= subtotal * (promocion.getDescuento() / 100);
        }

        // Establece el subtotal calculado
        detalle.setSubtotal(subtotal);

        return detalle;
    }


    private float aplicarDescuentoPorSuscripcion(float montoTotal, Usuario user) {
        if (!user.getSuscripcion().getNombre().equals("STANDAR")) {
            return montoTotal - (montoTotal * user.getSuscripcion().getDescuento() / 100);
        }
        return montoTotal;
    }
}