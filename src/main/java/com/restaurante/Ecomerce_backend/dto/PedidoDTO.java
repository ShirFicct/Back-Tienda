package com.restaurante.Ecomerce_backend.dto;

import com.restaurante.Ecomerce_backend.model.Detalle_Pedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "La fecha del pedido no puede ser nula")
    private Date fecha;

    @NotNull(message = "El estado del pedido no puede ser nulo")
    private String estado;

    @NotEmpty(message = "El monto total no puede estar vacío")
    private Float monto_total;


    @NotNull(message = "El usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "El método de pago no puede ser nulo")
    private Long idMetPago;

    @NotEmpty(message = "Debe contener al menos un detalle")
    private List<Detalle_Pedido> detalle;


}
