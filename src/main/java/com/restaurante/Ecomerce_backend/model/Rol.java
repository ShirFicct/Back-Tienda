package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Data  //almacena y genera automatic los get y set
@Table (name = "rol")   //da el nombre a la tabla
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //hac q las tuplas se generen incremental
    private Long id;
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "permiso_id", referencedColumnName = "id")
    )
    private Set<Permiso> permiso;

    public Rol() {}

    public Rol(String nombre) {
        super();
        this.nombre = nombre;
    }
}
