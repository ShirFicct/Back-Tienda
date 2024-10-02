package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Data
@Table (name= "promocion")
public class Promocion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String descripcion;
    private float descuento;

    @ManyToMany(mappedBy = "promociones")  // Relación inversa
    private Set<Suscripcion> suscripcions = new HashSet<>();
}
