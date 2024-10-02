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
@Table(name = "nota_venta")
public class Nota_venta implements Serializable {

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long id;
    @ManyToOne
    @JoinColumn(name = "Nro_pedido")
    private Pedido pedido;


    @ManyToOne
    @JoinColumn(name = "cod_producto")
    private Producto producto;
    private Long cantidad;
}
