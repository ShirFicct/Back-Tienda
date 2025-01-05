package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name = "inventario")

public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Nro;
    private int stock;
    private boolean activo;

        @ManyToOne
        @JoinColumn(name = "producto_id")
        private Producto producto;


        @ManyToOne
        @JoinColumn(name = "sucursal_id")
        private Sucursal sucursal;

        @ManyToOne
        @JoinColumn(name="talla_id")
        private Talla talla;

        @ManyToOne
        @JoinColumn(name="color_id")
        private Color color;

        // Getters y setters

}
