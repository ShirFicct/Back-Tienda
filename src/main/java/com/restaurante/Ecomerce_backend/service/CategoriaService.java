package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Categoria;
import com.restaurante.Ecomerce_backend.repositorios.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listCategoria() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerCatId(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria no encontrada"));
    }

    public Categoria crearCat(Categoria cat) {
        return categoriaRepository.save(cat);
    }

    public Categoria actualizarCat(Long id,Categoria cat) {
        Categoria categoria= obtenerCatId(id);
        categoria.setNombre(cat.getNombre());
        categoria.setDescripcion(cat.getDescripcion());
        return categoriaRepository.save(categoria);
    }

    public void eliminarCat(Long id) {
        categoriaRepository.deleteById(id);
    }

    }
