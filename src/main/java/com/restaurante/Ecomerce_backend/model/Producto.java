package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
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
    private Long stock;
    private float precio;


    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;

    @ManyToOne
    @JoinColumn(name = "id_temporada")
    private Temporada temporada;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Reporte_Sucursal> reporteSucursals;

}
