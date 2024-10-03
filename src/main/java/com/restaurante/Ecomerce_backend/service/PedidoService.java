package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.PedidoDTO;
import com.restaurante.Ecomerce_backend.model.Met_Pago;
import com.restaurante.Ecomerce_backend.model.Pedido;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.repositorios.Met_PagoRepository;
import com.restaurante.Ecomerce_backend.repositorios.PedidoRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;
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
    private UsuarioService usuarioService;

    @Autowired
    private Met_pagoService met_pagoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Met_PagoRepository met_pagoRepository;

    public List<Pedido> listPedido() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    // Crear un nuevo producto
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
       if (pedidoDTO== null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido no puede ser nula");
       }
        Usuario user = usuarioService.obtenerUserPorId(pedidoDTO.getUsuarioId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
        }
        Met_Pago metPago= met_pagoService.obtMetoPorId(pedidoDTO.getIdMetPago());
        if (metPago == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Método de pago no encontrado");
        }

        Pedido pedidonuevo=new Pedido();
        pedidonuevo.setFecha(pedidoDTO.getFecha());
        pedidonuevo.setEstado(pedidoDTO.isEstado());
        pedidonuevo.setMonto_total(pedidoDTO.getMonto_total());
        pedidonuevo.setUsuario(user);
        pedidonuevo.setMet_Pago(metPago);
        return pedidoRepository.save(pedidonuevo);
    }


    public Pedido modificarPedido(Long id, PedidoDTO pedido) {
        Pedido pedidoExistente= obtenerPedidoPorId(id);
        if (pedidoExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
        }
        Usuario user=usuarioRepository.findById(pedido.getUsuarioId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no fue encontrado"));

        Met_Pago metPago=met_pagoRepository.findById(pedido.getIdMetPago())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no fue encontrado"));

        pedidoExistente.setUsuario(user);
        pedidoExistente.setMet_Pago(metPago);
        pedidoExistente.setEstado(pedido.isEstado());
        pedidoExistente.setMonto_total(pedido.getMonto_total());
        pedidoExistente.setFecha(pedido.getFecha());

        return pedidoRepository.save(pedidoExistente);
    }

    // Eliminar un pedido
    public void eliminarPedido(Long id) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }
}
