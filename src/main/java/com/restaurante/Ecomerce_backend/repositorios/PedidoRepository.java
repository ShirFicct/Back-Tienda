package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidosByUsuarioId(Long id);
}
