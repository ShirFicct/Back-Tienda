package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProductoIdAndSucursalIdAndTallaIdAndColorId(
            Long productoId, Long sucursalId, Long tallaId, Long colorId);

    List<Inventario> findByProductoId(Long productoId);

    List<Inventario> findByProductoIdAndSucursalId(Long productoId, Long sucursalId);

    List<Inventario> findByProductoIdAndTallaIdAndColorId(Long productoId, Long tallaId, Long colorId);
}
