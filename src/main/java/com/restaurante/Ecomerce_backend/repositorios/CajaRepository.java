package com.restaurante.Ecomerce_backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.Ecomerce_backend.model.Caja;
@Repository
public interface CajaRepository extends JpaRepository < Caja,Long>
{
}
