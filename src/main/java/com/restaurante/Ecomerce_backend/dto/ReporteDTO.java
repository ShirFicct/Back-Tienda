package com.restaurante.Ecomerce_backend.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Nro;

    private Long idProducto;
    private Long idSucursal;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
}

