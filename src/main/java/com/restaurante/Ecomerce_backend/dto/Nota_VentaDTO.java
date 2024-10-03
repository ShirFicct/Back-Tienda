package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nota_VentaDTO {
    private long  idNota;
    @NotNull(message = "El número de pedido no puede ser nulo")
    private Long nroPedido;

    @NotNull(message = "El código del producto no puede ser nulo")
    private Long codProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    private Long cantidad;

}
