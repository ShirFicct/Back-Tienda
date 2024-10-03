package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long Nro_pedido;
    @NotNull(message = "La fecha del pedido no puede ser nula")
    private Date fecha;

    @NotNull(message = "El estado del pedido no puede ser nulo")
    private boolean estado;

    @NotEmpty(message = "El monto total no puede estar vacío")
    private String monto_total;

    @NotNull(message = "El usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "El método de pago no puede ser nulo")
    private Long idMetPago;



}
