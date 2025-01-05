package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Color;
import com.restaurante.Ecomerce_backend.repositorios.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> listarColores() {
        return colorRepository.findAll();
    }

    // Obtener un color por su ID
    public Color obtenerColorPorId(Long id) {
        return colorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Color no encontrado"));
    }

    // Crear un nuevo color
    public Color crearColor(Color color) {
        Color nuevoColor = new Color();
        nuevoColor.setNombre(color.getNombre());
        nuevoColor.setActivo(true);
        return colorRepository.save(nuevoColor);
    }

    // Actualizar un color existente
    public Color actualizarColor(Color color) {
        Color colorExistente = obtenerColorPorId(color.getId());
        colorExistente.setNombre(color.getNombre());
        return colorRepository.save(colorExistente);
    }

    // Eliminar (desactivar) un color
    public Color eliminarColor(Long id) {
        Color color = obtenerColorPorId(id);
        color.setActivo(false);
        return colorRepository.save(color);
    }
}
