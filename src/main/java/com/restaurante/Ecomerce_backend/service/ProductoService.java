package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    // Método para obtener un producto por su ID
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    // Método para guardar o actualizar un producto
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    // Método para eliminar un producto por su ID
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
