package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Data
@Table(name = "idioma")
public class Idioma  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;

    @OneToOne(mappedBy = "idioma")
    private Usuario usuario;
}
