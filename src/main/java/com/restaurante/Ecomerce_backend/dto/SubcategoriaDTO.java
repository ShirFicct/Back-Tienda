package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoriaDTO {
    private Long id;

    @NotEmpty(message = "ingrese un nombre")
    private String nombre;
    private Long idCategoria;
}
