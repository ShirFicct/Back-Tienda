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

    public List<IdiomaDTO> lisIdioma() {
        List<Idioma> idiomaNuevo = idiomaRepository.findAll();
        return idiomaNuevo.stream()
                .map(idioma -> modelMapper.map(idioma,IdiomaDTO.class))
                .collect(Collectors.toList());
    }
    public List<Idioma> listProductos() {
        return idiomaRepository.findAll();

    }

    // Obtener un producto por ID
    public Idioma obtenerIdiomaPorId(Long id) {
        return idiomaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Idioma no encontrado"));
    }

    // Crear un nuevo producto
    public Idioma crearIdioma(Idioma idioma) {
        return idiomaRepository.save(idioma);
    }

    public Idioma actualizarIdioma(Long id, Idioma idiomadetail) {
        Idioma idioma = obtenerIdiomaPorId(id);
        idioma.setNombre(idiomadetail.getNombre());
        return idiomaRepository.save(idioma);
    }

    public void eliminarIdioma(Long id) {
        Idioma idioma = obtenerIdiomaPorId(id);
        idiomaRepository.delete(idioma);

    }

}
