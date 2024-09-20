package com.restaurante.Ecomerce_backend.model;

import com.restaurante.Ecomerce_backend.model.Producto;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table (name= "suscripcion")
public class Suscripcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime Fecha_Inicio;
    private LocalDateTime Fecha_Fin;
    private float precio;
    private boolean estado;

    @ManyToMany
    @JoinTable(
            name = "beneficio",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "idSuscripcion"),  // FK de Producto en la tabla intermedia
            inverseJoinColumns = @JoinColumn(name = "idPromocion")  // FK de Categoría en la tabla intermedia
    )
    private Set<Promocion> promociones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_usuario")
private Usuario usuario;

}