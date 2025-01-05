package com.restaurante.Ecomerce_backend.response;

import com.restaurante.Ecomerce_backend.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	String token;
	String email;
	Rol role;
	String nombre;
	Long id;
	
}
