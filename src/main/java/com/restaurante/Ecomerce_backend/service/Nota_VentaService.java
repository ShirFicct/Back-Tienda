package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.Nota_VentaDTO;
import com.restaurante.Ecomerce_backend.model.Nota_venta;
import com.restaurante.Ecomerce_backend.model.Pedido;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.Nota_VentaRepository;
import com.restaurante.Ecomerce_backend.repositorios.PedidoRepository;
import com.restaurante.Ecomerce_backend.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class Nota_VentaService {
    @Autowired
    private Nota_VentaRepository nota_ventaRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<Nota_venta> listNota_venta() {
        return nota_ventaRepository.findAll();
    }

    public Nota_venta obtenerNotaPorId(Long id) {
        return nota_ventaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // Crear un nuevo producto
    public Nota_venta crearNota(Nota_VentaDTO notaDTO)
    { if (notaDTO == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota de venta no encontrado");
    }

        Pedido pedido = pedidoService.obtenerPedidoPorId(notaDTO.getNroPedido());
        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido no encontrado");
        }
        Producto producto = productoService.obtenerProductoPorId(notaDTO.getCodProducto());
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado");
        }
        Nota_venta nuevaNotaVenta = new Nota_venta();
        nuevaNotaVenta.setPedido(pedido);  // Asignar el pedido obtenido
        nuevaNotaVenta.setProducto(producto);  // Asignar el producto obtenido
        nuevaNotaVenta.setCantidad(notaDTO.getCantidad());  // Asignar la cantidad

        // Guardar la nota de venta en la base de datos
        return nota_ventaRepository.save(nuevaNotaVenta);

    }

    public Nota_venta actualizarNota(Long id, Nota_VentaDTO notaVentaDTO) {
        Nota_venta notaVenta = obtenerNotaPorId(id);

        if (notaVenta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La nota de venta no fue encontrada");
        }
        Pedido pedido = pedidoRepository.findById(notaVentaDTO.getNroPedido())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no fue encontrado"));

        Producto producto = productoRepository.findById(notaVentaDTO.getCodProducto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no fue encontrado"));

        notaVenta.setPedido(pedido);
        notaVenta.setProducto(producto);
        notaVenta.setCantidad(notaVentaDTO.getCantidad());  // Asignar la cantidad
        return nota_ventaRepository.save(notaVenta);
    }


}

