package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.ComboProducto;
import com.restaurante.Ecomerce_backend.repositorios.ComboProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComboProductoService {
    @Autowired
    private ComboProductoRepository comboProductoRepository;

    // Método para obtener todos los combos de productos
    public List<ComboProducto> findAll() {
        return comboProductoRepository.findAll();
    }

    // Método para obtener un combo de producto por su ID
    public Optional<ComboProducto> findById(Long id) {
        return comboProductoRepository.findById(id);
    }

    // Método para guardar o actualizar un combo
    public ComboProducto save(ComboProducto comboProducto) {
        return comboProductoRepository.save(comboProducto);
    }

    // Método para eliminar un combo por su ID
    public void deleteById(Long id) {
        comboProductoRepository.deleteById(id);
    }
}
