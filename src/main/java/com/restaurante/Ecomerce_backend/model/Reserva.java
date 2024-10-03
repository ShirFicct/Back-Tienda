package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "reserva")
public class Reserva  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Date fecha;
private boolean estado;

@ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

}
