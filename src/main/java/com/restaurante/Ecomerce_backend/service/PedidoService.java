package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Pedido;
import com.restaurante.Ecomerce_backend.repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listPedido() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    // Crear un nuevo producto
    public Pedido crearPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido modificarPedido(Long id, Pedido pedido) {
        Pedido pedidoExistente= obtenerPedidoPorId(pedido.getNro_pedido());
        pedidoExistente.setFecha(pedido.getFecha());
        pedidoExistente.setEstado(pedido.isEstado());
        pedidoExistente.setMonto_total(pedido.getMonto_total());
        pedidoExistente.setUsuario(pedido.getUsuario());
        pedidoExistente.setMet_Pago(pedido.getMet_Pago());
        return pedidoRepository.save(pedidoExistente);
    }

    // Eliminar un pedido
    public void eliminarPedido(Long id) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }
}
