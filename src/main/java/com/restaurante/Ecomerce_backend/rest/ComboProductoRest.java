package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.ComboProducto;
import com.restaurante.Ecomerce_backend.service.ComboProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comboProducto")
public class ComboProductoRest {
    @Autowired
    private ComboProductoService comboProductoService;

    // Obtener todos los combos de productos
    @GetMapping
    public List<ComboProducto> getAllCombos() {
        return comboProductoService.findAll();
    }

    // Obtener un combo de producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ComboProducto> getComboById(@PathVariable Long id) {
        Optional<ComboProducto> combo = comboProductoService.findById(id);
        return combo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar un combo de producto
    @PostMapping
    public ComboProducto createOrUpdateCombo(@RequestBody ComboProducto comboProducto) {
        return comboProductoService.save(comboProducto);
    }

    // Eliminar un combo de producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        comboProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
