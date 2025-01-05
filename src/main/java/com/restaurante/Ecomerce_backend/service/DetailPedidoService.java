package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.DetallePedidoDTO;
import com.restaurante.Ecomerce_backend.model.Detalle_Pedido;
import com.restaurante.Ecomerce_backend.model.Inventario;
import com.restaurante.Ecomerce_backend.model.Pedido;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.DetallePedRepository;
import com.restaurante.Ecomerce_backend.repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DetailPedidoService {
    @Autowired
    private DetallePedRepository detallePedido;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;
    @Autowired
    private InventarioService inventarioService;


    public List<Detalle_Pedido> findAll() {
        return detallePedido.findAll();
    }
    //obt detalles por pedido
    public List<Detalle_Pedido> obtByPedido(Long id_pedido) {
        Pedido pedido = pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new IllegalArgumentException("pedido no encontrada"));
        return detallePedido.findByPedidoId(pedido.getNro_pedido());


    }
    public Detalle_Pedido findById(Long id) {
        return detallePedido.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Detalle_Pedido crear(DetallePedidoDTO detallePedidoDTO) {
        Pedido pedido= pedidoService.obtenerPedidoPorId(detallePedidoDTO.getIdPedido());
        Producto producto=productoService.obtenerProductoPorId(detallePedidoDTO.getIdProducto());
        Inventario inv= inventarioService.findById(detallePedidoDTO.getIdInventario());
        Detalle_Pedido detalle_Pedido=new Detalle_Pedido();
        detalle_Pedido.setPedido(pedido);
        detalle_Pedido.setProducto(producto);
        detalle_Pedido.setInventario(inv);
        detalle_Pedido.setCantidad(detallePedidoDTO.getCantidad());
        detalle_Pedido.setPrecioUnitario(detallePedidoDTO.getPrecioUnitario());
        detalle_Pedido.setSubtotal(detallePedidoDTO.getPrecioUnitario()*detallePedidoDTO.getCantidad());
        return detallePedido.save(detalle_Pedido);
    }

    public Detalle_Pedido actualizar(Long id, DetallePedidoDTO detallePedidoDTO) {
       Detalle_Pedido detalle_Pedido = findById(id);
       detalle_Pedido.setCantidad(detallePedidoDTO.getCantidad());
       detalle_Pedido.setSubtotal(detallePedidoDTO.getCantidad()*detallePedidoDTO.getPrecioUnitario());
       detalle_Pedido.setPrecioUnitario(detallePedidoDTO.getPrecioUnitario());
       return detallePedido.save(detalle_Pedido);

    }

    public void eliminar(Long id) {
        Detalle_Pedido detalle_Pedido = findById(id);
        detalle_Pedido.setActivo(false);
        detallePedido.save(detalle_Pedido);
    }

}

