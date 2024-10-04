package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.UsuarioDTO;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.response.AuthResponse;
import com.restaurante.Ecomerce_backend.response.LoginRequest;
import com.restaurante.Ecomerce_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AuthResponse> getCurrentUser(Authentication authentication) {
		return ResponseEntity.ok(userService.loader(authentication));
	}
}
