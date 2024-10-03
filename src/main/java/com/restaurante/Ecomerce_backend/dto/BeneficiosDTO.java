package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiosDTO {
    private Long id;

    private Long idPromo;

    private Long idSuscripcion;
    private int descuento;
}
