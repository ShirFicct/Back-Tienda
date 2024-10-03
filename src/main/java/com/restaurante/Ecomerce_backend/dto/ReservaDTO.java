package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    @NotNull(message = "La fecha del pedido no puede ser nula")
    private Date fecha;

    @NotNull(message = "El estado del pedido no puede ser nulo")
    private boolean estado;

    @NotNull(message = "El usuario no puede ser nulo")
    private Long idUsuario;
}
