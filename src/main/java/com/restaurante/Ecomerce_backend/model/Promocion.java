package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Data
@Table (name= "promocion")
public class Promocion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private boolean activo;
    private String descripcion;
    private Date fecha_Inicio;
    private Date fecha_Fin;
    private float descuento;

    @ManyToOne
    @JoinColumn(name="id_suscripcion")
    private Suscripcion suscripcion;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL)
    private List<Producto> ProdcutoList;


}
