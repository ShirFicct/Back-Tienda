package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long codProducto; // Puede ser nulo al crear un nuevo producto

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;
    private List<String> imagenes;
    @NotNull(message = "El precio no puede ser nulo")
    private float precio;

    private Long id_marca;
    private Long idSubcategoria;
    private Long idTemporada;
    private  Long idPromocion;
}
