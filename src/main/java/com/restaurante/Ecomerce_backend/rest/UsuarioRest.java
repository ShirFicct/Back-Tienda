package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.UsuarioDTO;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.response.AuthResponse;
import com.restaurante.Ecomerce_backend.service.UsuarioService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UsuarioRest {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuarios() {
		List<Usuario> user = usuarioService.listUsuario();
		return new ResponseEntity<>(
				ApiResponse.<List<Usuario>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(user)
						.build(),
				HttpStatus.OK
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Usuario>> obtenerUsuario(@PathVariable Long id) {
		Usuario usuariosOpt = usuarioService.obtenerUserPorId(id);
		if (usuariosOpt == null) {
			return new ResponseEntity<>(ApiResponse.<Usuario>builder()
					.statusCode(HttpStatus.NOT_FOUND.value())
					.message("Usuario no encontrado")
					.build(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ApiResponse.<Usuario>builder()
				.statusCode(HttpStatus.OK.value())
				.message(HttpStatusMessage.getMessage(HttpStatus.OK))
				.data(usuariosOpt)
				.build(),
				HttpStatus.OK
		);
	}


	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO userDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					ApiResponse.<Usuario>builder()
							.errors(errors)
							.build(),
					HttpStatus.BAD_REQUEST
			);
		}

		Usuario usuarioActualizado = usuarioService.updateUser(id, userDTO); // Cambiado
		return new ResponseEntity<>(
				ApiResponse.<Usuario>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(usuarioActualizado)
						.build(),
				HttpStatus.OK
		);
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long id) {
		usuarioService.deleteUser(id);
		return new ResponseEntity<>(
				ApiResponse.<Void>builder()
						.statusCode(HttpStatus.NO_CONTENT.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
						.build(),
				HttpStatus.NO_CONTENT
		);
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<ApiResponse<Void>> activarUsuario(@PathVariable Long id) {
		usuarioService.activeUser(id);
		return new ResponseEntity<>(
				ApiResponse.<Void>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.build(),
				HttpStatus.OK
		);
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AuthResponse> getCurrentUser(Authentication authentication) {
		return ResponseEntity.ok(usuarioService.loader(authentication));
	}

}
