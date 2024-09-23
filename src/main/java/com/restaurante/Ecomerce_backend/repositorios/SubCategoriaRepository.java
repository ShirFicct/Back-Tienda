package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Categoria;
import com.restaurante.Ecomerce_backend.model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoriaRepository extends JpaRepository<Subcategoria, Long> {
    Optional<Subcategoria> findByNombre(String nombre);
    List<Subcategoria> findByCategoria(Categoria categoria);
}
