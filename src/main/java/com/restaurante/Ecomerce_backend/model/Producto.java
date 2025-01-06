package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table (name= "producto")
public class Producto  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long codProducto;
    private String nombre;
    private String descripcion;
    private float precio;
    private boolean activo;

    @ElementCollection // Anotación para almacenar listas simples en la base de datos
    @CollectionTable(name = "producto_imagenes", joinColumns = @JoinColumn(name = "codProducto"))
    @Column(name = "imagen_url") // Nombre de la columna para cada URL
    private List<String> imagenes = new ArrayList<>(); // Lista de URLs de las imágenes

    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;

    @ManyToOne
    @JoinColumn(name= "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_temporada")
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name="id_promocion")
    private Promocion promocion;
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Inventario> inventarioList;

}
