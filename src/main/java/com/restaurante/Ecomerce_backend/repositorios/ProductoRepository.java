package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findBySubcategoria_Id(Long idSubcategoria);

    List<Producto> findByTemporada_Id(Long idTemporada);

    List<Producto> findByMarca_Id(Long idMarca);
    List<Producto> findByPromocion_Id(Long idPromo);
}
