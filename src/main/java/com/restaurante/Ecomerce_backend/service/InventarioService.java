package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.InventarioDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private TallaService tallaService;

    @Autowired
    private ColorService colorService;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "inventario no encontrado"));
    }

    public Inventario crear(InventarioDTO inventarioDTO) {
        Producto producto = productoService.obtenerProductoPorId(inventarioDTO.getProductoId());
        Sucursal sucursal = sucursalService.obtSucursal(inventarioDTO.getSucursalId());
        Talla talla = tallaService.obtenerTallaPorId(inventarioDTO.getTallaId());
        Color color = colorService.obtenerColorPorId(inventarioDTO.getColorId());

        Inventario inv = new Inventario();
        inv.setActivo(true);
        inv.setProducto(producto);
        inv.setSucursal(sucursal);
        inv.setTalla(talla);
        inv.setColor(color);
        inv.setStock(inventarioDTO.getStock());
        inventarioRepository.save(inv);
        return inv;
    }

    public Inventario update(Long id, InventarioDTO inventarioDTO) {
        Inventario inventario = findById(id);
        Producto producto = productoService.obtenerProductoPorId(inventarioDTO.getProductoId());
        Talla talla = tallaService.obtenerTallaPorId(inventarioDTO.getTallaId());
        Color color = colorService.obtenerColorPorId(inventarioDTO.getColorId());

        inventario.setStock(inventarioDTO.getStock());
        inventario.setProducto(producto);
        inventario.setColor(color);
        inventario.setTalla(talla);

        inventarioRepository.save(inventario);
        return inventario;
    }

    public List<Inventario> obtenerInventariosPorProducto(Long productoId) {
        Producto producto = productoService.obtenerProductoPorId(productoId);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        return producto.getInventarioList();
    }
    public void decrementarStockPorPagoConfirmado(Long productoId, Long almacenId, Long tallaId, Long colorId, int cantidad) {
        Inventario inventario = inventarioRepository.findByProductoIdAndSucursalIdAndTallaIdAndColorId(
                        productoId, almacenId, tallaId, colorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));

        if (inventario.getStock() < cantidad) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        inventario.setStock(inventario.getStock() - cantidad);
        inventarioRepository.save(inventario);
    }

    public void delete(Long id) {
        Inventario inventario = findById(id);
        inventario.setActivo(false);
        inventarioRepository.save(inventario);

    }

}
