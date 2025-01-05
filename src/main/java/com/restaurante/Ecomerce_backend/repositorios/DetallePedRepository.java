package com.restaurante.Ecomerce_backend.repositorios;

import com.restaurante.Ecomerce_backend.model.Detalle_Pedido;
import com.restaurante.Ecomerce_backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedRepository extends JpaRepository<Detalle_Pedido,Long> {
List<Detalle_Pedido> findByPedidoId(Long Id_pedido);
void deleteByPedidoId(Long Id_pedido);
}
