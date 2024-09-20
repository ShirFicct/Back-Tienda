package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table (name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String direccion;
    private int telefono;

    @ManyToOne
    @JoinColumn(name ="id_rol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Suscripcion suscripcion;

    @OneToOne
    @JoinColumn(name = "id_idioma") //foranea
    private Idioma idioma;
}
