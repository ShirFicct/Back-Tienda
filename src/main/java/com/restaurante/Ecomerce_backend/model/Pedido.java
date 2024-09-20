package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name = "pedidos")
public class Pedido {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long Nro_pedido;
private Date fecha;
private String monto_total;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
