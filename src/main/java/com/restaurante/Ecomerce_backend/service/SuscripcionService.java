package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Suscripcion;
import com.restaurante.Ecomerce_backend.repositorios.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SuscripcionService {
    @Autowired
    private SuscripcionRepository suscripcionRepository;

    public List<Suscripcion> listSuscripciones() {
        return suscripcionRepository.findAll();
    }

    public Suscripcion obtSuscripcionById(Long id) {
        return suscripcionRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripcion no encontrado"));

    }

    public Suscripcion crearSuscripcion(Suscripcion suscripcion) {
        return suscripcionRepository.save(suscripcion);
    }

    public Suscripcion modificarSuscripcion(Long id, Suscripcion suscripcion) {
        Suscripcion suscripcion1 = obtSuscripcionById(suscripcion.getId());
        suscripcion1.setNombre(suscripcion.getNombre());
        suscripcion1.setDescripcion(suscripcion.getDescripcion());
        suscripcion1.setFecha_Inicio(suscripcion.getFecha_Inicio());
        suscripcion1.setFecha_Fin(suscripcion.getFecha_Fin());
        suscripcion1.setPrecio(suscripcion.getPrecio());
        suscripcion1.setEstado(suscripcion.isEstado());
        return suscripcionRepository.save(suscripcion1);

    }

    public void eliminarSuscripcion(Long id) {
        Suscripcion suscripcion = obtSuscripcionById(id);
        suscripcionRepository.delete(suscripcion);
    }
}