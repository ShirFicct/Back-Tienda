package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleReservaDTO {
    private Integer cantidad;
    private float precioUnitario;
    private Float subtotal;
    private Long idReserva;
    private Long idProducto;

    private Long sucursalId;
    private Long tallaId;
    private Long colorId;
}
