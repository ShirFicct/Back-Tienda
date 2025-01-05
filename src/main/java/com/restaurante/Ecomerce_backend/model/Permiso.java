package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permiso")
@Entity
public class Permiso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 

	private String nombre;
	
}
