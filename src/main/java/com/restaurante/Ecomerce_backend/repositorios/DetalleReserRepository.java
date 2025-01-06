package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Detalle_reseva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReserRepository extends JpaRepository<Detalle_reseva,Long> {

    @Query("SELECT d FROM Detalle_reseva d WHERE d.reserva.id = :reservaId")
    List<Detalle_reseva> findByReservaId(@Param("reservaId") Long reservaId);
}
