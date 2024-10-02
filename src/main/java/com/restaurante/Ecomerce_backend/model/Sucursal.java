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
@Table (name= "sucursal")
public class Sucursal  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    private List<Reporte_Sucursal> reporteSucursals;



}
