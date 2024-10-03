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
@Table(name = "productoReserva")
public class ProductoReseva implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codProd")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idReserva")
    private Reserva reserva;

    private int cantidad;
}
