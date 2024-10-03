package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@Table(name = "productoPromo")
public class ProductoPromo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="codigoP")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="idPromo")
    private Promocion promo;

    private boolean estado;
}
