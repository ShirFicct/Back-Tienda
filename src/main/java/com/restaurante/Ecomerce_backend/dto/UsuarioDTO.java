package com.restaurante.Ecomerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	
	private Long id;

    @NotEmpty(message = "ingrese un nombre")
    private String nombre;

    @Email(message = "El email debe ser válido")
    private String email;

    
   @NotEmpty(message = "ingrese su contraseña")
    private String password;

   private String direccion;
   private String telefono;
   private String Nit;
private  Long idRol;
private Long id_Suscripcion;
private Long id_Idioma;



}
