package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoRest {
    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.findById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar un producto
    @PostMapping
    public Producto createOrUpdateProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
