package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table (name= "producto")
public class Producto  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;
    private String nombre;
    private String descripcion;
    private String marca;
    private String talla;
    private String color;
    private String imagen;
    private float precio;


    @ManyToOne
    @JoinColumn(name ="id_subcategoria")
    private Subcategoria subcategoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Inventario> inventarios;


}
