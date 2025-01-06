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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UsuarioRest {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
	public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuarios(@RequestParam(value = "search", required = false) String searchTerm) {
		List<Usuario> user;
		if (searchTerm != null && !searchTerm.isEmpty()) {
			user = usuarioService.buscarUsuarios(searchTerm);
		} else {
			user = usuarioService.listUsuario();
		}
		return new ResponseEntity<>(
				ApiResponse.<List<Usuario>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(user)
						.build(),
				HttpStatus.OK
		);
	}

	@GetMapping("/protected")
	public ResponseEntity<String> checkAuthorities() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getAuthorities());

		return ResponseEntity.ok("Acceso concedido");
	}



	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
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
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
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
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
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
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
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
	@GetMapping("/suscripcion/{suscripcionId}")
	public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuariosPorSuscripcion(@PathVariable Long suscripcionId) {
		List<Usuario> usuarios = usuarioService.obtUserBySus(suscripcionId);

		return new ResponseEntity<>(
				ApiResponse.<List<Usuario>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(usuarios)
						.build(),
				HttpStatus.OK
		);
	}
	@GetMapping("/byName")
	public ResponseEntity<ApiResponse<Long>> obtenerUsuarioIdPorNombre(@RequestParam String name) {
		Long userId = usuarioService.getUsuariorByname(name);

		return new ResponseEntity<>(
				ApiResponse.<Long>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(userId)
						.build(),
				HttpStatus.OK
		);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('PERMISO_GESTIONAR_PERSONAL')")
	public ResponseEntity<ApiResponse<Usuario>> guardarUsuario(@Valid @RequestBody UsuarioDTO grupoDTO, BindingResult bindingResult) {
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
		try {
			Usuario user = usuarioService.registrarClient(grupoDTO);
			return new ResponseEntity<>(
					ApiResponse.<Usuario>builder()
							.statusCode(HttpStatus.CREATED.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
							.data(user)
							.build(),
					HttpStatus.CREATED
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Usuario>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}
}
