package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Permiso;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.PermisoService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permiso")
public class PermisoRest {
	
	@Autowired
	private PermisoService permisoService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Permiso>>> listarPermisos() {
		List<Permiso> rol = permisoService.listarPermiso();
		return new ResponseEntity<>(
				ApiResponse.<List<Permiso>>builder()
						.statusCode(HttpStatus.OK.value())
						.message(HttpStatusMessage.getMessage(HttpStatus.OK))
						.data(rol)
						.build(),
				HttpStatus.OK
		);
	}
    
//	@GetMapping("/{id}")
//	public ResponseEntity<ApiResponse<Cliente>> obtenerCliente(@PathVariable Long id) {
//		try {
//			Cliente usuariosOpt = service.obtenerClientePorId(id);
//			return new ResponseEntity<>(
//					ApiResponse.<Cliente>builder()
//							.statusCode(HttpStatus.OK.value())
//							.message(HttpStatusMessage.getMessage(HttpStatus.OK))
//							.data(usuariosOpt)
//							.build(),
//					HttpStatus.OK
//			);
//		} catch (ResponseStatusException e) {
//			return new ResponseEntity<>(
//					ApiResponse.<Cliente>builder()
//							.statusCode(e.getStatusCode().value())
//							.message(e.getReason())
//							.build(),
//					e.getStatusCode()
//			);
//		}
//	}
    

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Permiso>>actualizarPermiso(@PathVariable Long id, @Valid @RequestBody Permiso permiso, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.errors(errors)
							.build(),
					HttpStatus.BAD_REQUEST
			);
		}
		try {
			Permiso permisoActualizado = permisoService.actualizarPermiso(id, permiso);
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.statusCode(HttpStatus.OK.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.OK))
							.data(permisoActualizado)
							.build(),
					HttpStatus.OK
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Permiso>> guardarPermiso(@Valid @RequestBody Permiso permiso, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.errors(errors)
							.build(),
					HttpStatus.BAD_REQUEST
			);
		}
		try {
			Permiso permission = permisoService.guardarPermiso(permiso);
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.statusCode(HttpStatus.CREATED.value())
							.message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
							.data(permission)
							.build(),
					HttpStatus.CREATED
			);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(
					ApiResponse.<Permiso>builder()
							.statusCode(e.getStatusCode().value())
							.message(e.getReason())
							.build(),
					e.getStatusCode()
			);
		}
	}

}
