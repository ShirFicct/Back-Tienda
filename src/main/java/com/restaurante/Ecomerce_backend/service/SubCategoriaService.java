package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.SubcategoriaDTO;
import com.restaurante.Ecomerce_backend.model.Categoria;
import com.restaurante.Ecomerce_backend.model.Subcategoria;
import com.restaurante.Ecomerce_backend.repositorios.CategoriaRepository;
import com.restaurante.Ecomerce_backend.repositorios.SubCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

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
    public Subcategoria crearSubCat(SubcategoriaDTO subcategoria) {
        // Validar que subcategoria no sea nulo (opcional)
        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategoría no puede ser nula");

        }
        Categoria categoria = categoriaService.obtenerCatId(subcategoria.getIdCategoria());

        Set<Categoria> categorias = new HashSet<>();
        categorias.add(categoria);
        Subcategoria subcategoriaSubcategoria = new Subcategoria();
        subcategoriaSubcategoria.setCategoria(categoria);
        subcategoriaSubcategoria.setNombre(subcategoria.getNombre());


        return subCategoriaRepository.save(subcategoriaSubcategoria);
    }

    // Actualizar una subcategoría existente
    public Subcategoria actualizarSubCat(Long id, SubcategoriaDTO subcategoriaDetalles) {
        Subcategoria subcategoria = obtenerSubCatId(id);

        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La subcategoria no fue encontrada");
        }
        Categoria categoria= categoriaRepository.findById(subcategoriaDetalles.getIdCategoria()).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no fue encontrado"));
        subcategoria.setCategoria(categoria);
        subcategoria.setNombre(subcategoriaDetalles.getNombre());
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
