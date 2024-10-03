package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long codProducto; // Puede ser nulo al crear un nuevo producto

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    private String marca;

    private String talla;

    private String color;

    private String imagen;

    @NotNull(message = "El stock no puede ser nulo")
    private Long stock;

    @NotNull(message = "El precio no puede ser nulo")
    private float precio;

    private Long idSubcategoria;
    private Long idTemporada;
}
