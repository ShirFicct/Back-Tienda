package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pedido_usuario_producto")
public class Detalle_Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_producto_seq")
    @SequenceGenerator(name = "pedido_producto_seq", sequenceName = "pedido_usuario_producto_seq", allocationSize = 50)
    private Long id;

    private boolean activo;
    private Integer cantidad;
    private float precioUnitario;
    private Float subtotal;

    @ManyToOne
    @JoinColumn(name = "id_pedido",nullable = false)
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_producto",nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;


}
