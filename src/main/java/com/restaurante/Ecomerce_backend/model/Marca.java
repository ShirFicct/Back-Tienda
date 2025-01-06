package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private boolean activo;
}
