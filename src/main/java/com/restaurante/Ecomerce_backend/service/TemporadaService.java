package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Temporada;
import com.restaurante.Ecomerce_backend.repositorios.TemporadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TemporadaService {
    @Autowired
    private TemporadaRepository temporadaRepository;

    // Listar todas las temporadas
    public List<Temporada> listarTemporadas() {
        return temporadaRepository.findAll();
    }

    // Obtener una temporada por ID
    public Temporada obtenerTemporadaPorId(Long id) {
        return temporadaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Temporada no encontrada"));
    }

    // Crear una nueva temporada
    public Temporada crearTemporada(Temporada temporada) {
        Temporada nuevaTemporada = new Temporada();
        nuevaTemporada.setNombre(temporada.getNombre());
        nuevaTemporada.setActivo(true);
        return temporadaRepository.save(nuevaTemporada);
    }

    // Modificar una temporada existente
    public Temporada modificarTemporada(Long id, Temporada temporada) {
        Temporada temporadaExistente = obtenerTemporadaPorId(id);
        temporadaExistente.setNombre(temporada.getNombre());
        temporadaExistente.setActivo(temporada.isActivo());
        return temporadaRepository.save(temporadaExistente);
    }

    // Eliminar una temporada (lógica, no física)
    public Temporada eliminarTemporada(Long id) {
        Temporada temporada = obtenerTemporadaPorId(id);
        temporada.setActivo(false);
        return temporadaRepository.save(temporada);
    }
}
