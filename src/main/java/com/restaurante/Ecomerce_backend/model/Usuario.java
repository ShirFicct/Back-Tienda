package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table (name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String ci;
    private String nombre;
    private String correo;
    private String password;
    private String direccion;
    private int telefono;

    @ManyToOne
    @JoinColumn(name ="id_rol")
    private Rol rol;

}
