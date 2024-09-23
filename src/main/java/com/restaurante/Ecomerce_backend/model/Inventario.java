package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
@Getter
@Setter
@Table(name = "inventario")

public class Inventario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

        private Integer stock;
        private LocalDate fechaInicio;
        private LocalDate fechaFinal;

        @ManyToOne
        @JoinColumn(name = "producto_id")
        private Producto producto;

        @ManyToOne
        @JoinColumn(name = "sucursal_id")
        private Sucursal sucursal;

        // Getters y setters


    public Inventario() {}
}
