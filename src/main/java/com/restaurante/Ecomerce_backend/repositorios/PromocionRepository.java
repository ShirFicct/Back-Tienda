package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
List<Promocion>findBySuscripcionId(Long suscripcionId);
}
