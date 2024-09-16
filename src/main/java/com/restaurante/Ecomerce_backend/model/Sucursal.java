package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data  //almacena y genera automatic los get y set
@Table (name = "Sucursal")
public class Sucursal  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String codigo; //?


    private Integer nit;

    private String nombre;
    private String razon_social; //??
}
