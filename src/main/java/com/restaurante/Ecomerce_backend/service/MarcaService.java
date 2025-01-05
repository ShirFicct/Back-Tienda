package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Marca;
import com.restaurante.Ecomerce_backend.repositorios.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    // Obtener todas las marcas
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    // Obtener una marca por su ID
    public Marca obtMarcaById(Long id) {
        return marcaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "marca no encontrado"));
    }

    // Crear una nueva marca
    public Marca createMarca(Marca marca) {
        Marca marcaSalvo = new Marca();
        marcaSalvo.setNombre(marca.getNombre());
        marcaSalvo.setActivo(true);
        return marcaRepository.save(marca);
    }

    // Actualizar una marca existente
    public Marca updateMarca(Long id, Marca marcaDetails) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        marca.setNombre(marcaDetails.getNombre());
        return marcaRepository.save(marca);
    }

    // Eliminar una marca por su ID
    public void deleteMarca(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        marca.setActivo(false);
        marcaRepository.save(marca);
    }
}
