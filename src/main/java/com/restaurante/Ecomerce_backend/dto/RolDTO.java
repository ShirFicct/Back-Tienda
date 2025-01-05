package com.restaurante.Ecomerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
	
	private String nombre;
    private List<String> nombrePermiso;

}
