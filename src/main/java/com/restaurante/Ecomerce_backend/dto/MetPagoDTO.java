package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;

public class MetPagoDTO {
    private Long codigo;

    @NotEmpty(message = "ingrese un nombre")
    private String nombre;
}
