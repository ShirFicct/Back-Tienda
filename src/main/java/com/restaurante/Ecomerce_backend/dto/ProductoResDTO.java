package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResDTO {
    private Long id;

    private Long codProd;
    private Long idReserva;


    private int cantidad;
}
