package com.restaurante.Ecomerce_backend.model;

import com.restaurante.Ecomerce_backend.model.Producto;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Data
@Table (name= "suscripcion")
public class Suscripcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String descripcion;
    private float precio;
    private boolean estado;
    private float descuento;




    @OneToMany(mappedBy = "suscripcion",cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuario;

    @OneToMany(mappedBy = "suscripcion",cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Promocion> promocions;


}