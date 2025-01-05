package com.restaurante.Ecomerce_backend.repositorios;


import com.restaurante.Ecomerce_backend.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

}
