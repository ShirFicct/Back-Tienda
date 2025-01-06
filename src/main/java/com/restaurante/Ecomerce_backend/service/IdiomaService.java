package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.IdiomaDTO;
import com.restaurante.Ecomerce_backend.model.Idioma;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.IdiomaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class IdiomaService {
    @Autowired
    private IdiomaRepository idiomaRepository;


    @Autowired
    private ModelMapper modelMapper;

    public List<Idioma>listarIdioma(){
        return idiomaRepository.findAll();
    }


    // Obtener un producto por ID
    public Idioma obtenerIdiomaPorId(Long id) {
        return idiomaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Idioma no encontrado"));
    }

    // Crear un nuevo producto
    public Idioma crearIdioma(Idioma idioma) {
        Idioma newidioma = new Idioma();
        newidioma.setNombre(idioma.getNombre());
        newidioma.setActivo(true);
        return idiomaRepository.save(idioma);
    }

    public Idioma actualizarIdioma(Long id, Idioma idiomadetail) {
        Idioma idioma = obtenerIdiomaPorId(id);
        idioma.setNombre(idiomadetail.getNombre());
        return idiomaRepository.save(idioma);
    }

    public Idioma eliminarIdioma(Long id) {
        Idioma idioma = obtenerIdiomaPorId(id);
        idioma.setActivo(false);
        return idiomaRepository.save(idioma);

    }

}
