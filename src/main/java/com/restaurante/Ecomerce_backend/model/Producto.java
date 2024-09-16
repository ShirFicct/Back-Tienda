package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table (name= "producto")
public class Producto  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private float precio;

    // Relación hacia los productos en el combo
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private Set<ComboProducto> combos;

    // Si quieres saber en qué combos está este producto, puedes agregar otra relación inversa
    @OneToMany(mappedBy = "comboProducto", cascade = CascadeType.ALL)
    private Set<ComboProducto> estaEnCombos; // Aquí guardamos los productos que forman parte del combo

}
