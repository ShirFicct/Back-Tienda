package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "combo_producto")
public class ComboProducto {



    // Relación ManyToOne hacia Producto como parte del combo
    @ManyToOne
    @JoinColumn(name = "producto_id")
    @Id
    private Producto producto;

    // Relación ManyToOne hacia Producto que forma parte del combo
    @ManyToOne
    @JoinColumn(name = "combo_producto_id")
    private Producto comboProducto;

    // Puedes añadir más atributos aquí si lo necesitas, por ejemplo, cantidad o precio especial del combo

}
