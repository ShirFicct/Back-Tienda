package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Data
@Table(name= "beneficio")
public class Beneficio  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name="idPromo")
    private Promocion promo;

    @ManyToOne
    @JoinColumn(name="idSuscripcion")
    private Suscripcion suscripcion;
    private int descuento;
}
