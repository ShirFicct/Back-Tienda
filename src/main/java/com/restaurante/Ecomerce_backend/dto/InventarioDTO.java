package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    private int stock;
    private Long productoId;
    private Long SucursalId;
    private Long tallaId;
    private Long colorId;
}
