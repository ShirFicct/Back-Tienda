package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.UsuarioDTO;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.response.AuthResponse;
import com.restaurante.Ecomerce_backend.response.LoginRequest;
import com.restaurante.Ecomerce_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegistroController {
	
	@Autowired
	private UsuarioService userService;

	@PostMapping(value = "login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(userService.login(request));
	}
	
	@PostMapping(value = "register")
	public ResponseEntity<AuthResponse> register(@RequestBody UsuarioDTO userDto) {
		return ResponseEntity.ok(userService.createUser(userDto));
	}

	//Register client
	@PostMapping(value = "registerClient")
	public ResponseEntity<Usuario> registerClient(@RequestBody UsuarioDTO userDto) {
		return ResponseEntity.ok(userService.registrarUser(userDto));
	}
	
	@PostMapping(value = "registerAdmin")
	public ResponseEntity<AuthResponse> registerAdmin(@RequestBody UsuarioDTO userDto) {
		return ResponseEntity.ok(userService.createUserAdmin(userDto));
	}

}
