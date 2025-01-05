package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long>{
}
