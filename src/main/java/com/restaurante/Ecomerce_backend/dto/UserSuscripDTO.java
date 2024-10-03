package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuscripDTO {
    private Long id;

    private Long idSuscripcion;

    private Long idUsuario;
    private Date fechaInicio;
    private Date fechaFin;

    private boolean estado;
}
