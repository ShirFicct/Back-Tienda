package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO {
    private Integer cantidad;
    private float precioUnitario;
    private Float subtotal;
    private Long idPedido;
    private Long idProducto;
    private Long idInventario;

}
