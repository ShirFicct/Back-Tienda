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

    public List<Temporada> listTemporadas() {
        return temporadaRepository.findAll();
    }

    public Temporada obtTemporadaId(Long id) {
        return temporadaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Temporada no encontrado"));
    }

    public Temporada createTemporada(Temporada temporada) {
        return temporadaRepository.save(temporada);
    }
    public Temporada modificarTemporada(Long id, Temporada temporada) {
        Temporada temporada1=obtTemporadaId(temporada.getId());
        temporada1.setNombre(temporada.getNombre());
        return temporadaRepository.save(temporada1);
    }

    public void eliminarTemporada(Long id) {
        temporadaRepository.deleteById(id);
    }
}
