package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "detalle_reserva")
public class Detalle_reseva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer cantidad;
    private float precioUnitario;
    private Float subtotal;

    private boolean estado;
    @ManyToOne
    @JoinColumn(name = "id_producto",nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_reserva",nullable = false)
    private Reserva reserva;
}


