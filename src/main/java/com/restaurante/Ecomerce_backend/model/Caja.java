package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data  //almacena y genera automatic los get y set
@Table (name = "Caja")
public class Caja  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
   private String nombre;

   @ManyToOne
   @JoinColumn(name = "id_sucursal")
   private Sucursal id_sucursal;
}
