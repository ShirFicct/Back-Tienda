package com.restaurante.Ecomerce_backend.dto;

import com.restaurante.Ecomerce_backend.model.Promocion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPromoDTO {
    private Long id;

    private Long codigoP;
    private Long idPromo;

    private boolean estado;
}
