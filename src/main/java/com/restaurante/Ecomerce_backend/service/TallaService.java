package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Idioma;
import com.restaurante.Ecomerce_backend.model.Talla;
import com.restaurante.Ecomerce_backend.repositorios.TallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TallaService {
    @Autowired
    private TallaRepository tallaRepository;

    public List<Talla> listarTallas(){
        return tallaRepository.findAll();
    }
    public Talla obtenerTallaPorId(Long id) {
        return tallaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Talla no encontrado"));
    }

    public Talla crearTalla(Talla talla) {
      Talla newt= new Talla();
      newt.setNombre(talla.getNombre());
      newt.setActivo(true);
        return tallaRepository.save(talla);
    }
    public Talla actualizarTalla(Talla talla) {
        Talla newt= new Talla();
        newt.setNombre(talla.getNombre());
        return tallaRepository.save(talla);
    }

    public Talla eliminarTalla(Long id) {
        Talla talla = obtenerTallaPorId(id);
        talla.setActivo(false);
        return tallaRepository.save(talla);
    }

}
