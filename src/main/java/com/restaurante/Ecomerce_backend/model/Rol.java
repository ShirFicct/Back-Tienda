package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data  //almacena y genera automatic los get y set
@Table (name = "rols")   //da el nombre a la tabla
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //hac q las tuplas se generen incremental
    private Long id;
    private String nombre;
}
