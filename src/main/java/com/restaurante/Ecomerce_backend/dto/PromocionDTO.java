package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocionDTO {
    private String nombre;
    private String descripcion;
    private Date fecha_Inicio;
    private Date fecha_Fin;
    private Long suscripcion;
    private float descuento;
}
