package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    // Listar todos los productos
    public List<Producto> listProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar un producto
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        Producto producto = obtenerProductoPorId(id);
        producto.setNombre(productoDetalles.getNombre());
        producto.setDescripcion(productoDetalles.getDescripcion());
        producto.setMarca(productoDetalles.getMarca());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setTalla(productoDetalles.getTalla());
        producto.setColor(productoDetalles.getColor());
        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }
}
