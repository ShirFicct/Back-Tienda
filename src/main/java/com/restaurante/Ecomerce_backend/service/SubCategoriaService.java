package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Subcategoria;
import com.restaurante.Ecomerce_backend.repositorios.SubCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    // Listar todas las subcategorías
    public List<Subcategoria> listSubCategorias() {
        return subCategoriaRepository.findAll();
    }

    // Obtener subcategoría por ID
    public Subcategoria obtenerSubCatId(Long id) {
        return subCategoriaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcategoría no encontrada"));
    }

    // Crear una nueva subcategoría
    public Subcategoria crearSubCat(Subcategoria subcategoria) {
        // Validar que subcategoria no sea nulo (opcional)
        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategoría no puede ser nula");
        }
        return subCategoriaRepository.save(subcategoria);
    }

    // Actualizar una subcategoría existente
    public Subcategoria actualizarSubCat(Long id, Subcategoria subcategoriaDetalles) {
        Subcategoria subcategoria = obtenerSubCatId(id);
        subcategoria.setNombre(subcategoriaDetalles.getNombre());
        subcategoria.setCategoria(subcategoriaDetalles.getCategoria());
        return subCategoriaRepository.save(subcategoria);
    }

    // Eliminar una subcategoría
    public void eliminarSubCat(Long id) {
        if (!subCategoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcategoría no encontrada");
        }
        subCategoriaRepository.deleteById(id);
    }
}
