package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.RolDTO;
import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.RolService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/roles")
public class RolRest {

    @Autowired
    private RolService rolService;

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<ApiResponse<List<Rol>>> listarRoles() {
        List<Rol> rol = rolService.listarRoles();
        return new ResponseEntity<>(
                ApiResponse.<List<Rol>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(rol)
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> obtenerRol(@PathVariable Long id) {
        try {
            Rol rol = rolService.obtenerRol(id);
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(rol)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>>actualizarRol(@PathVariable Long id, @Valid @RequestBody RolDTO rolDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Rol rolActualizado = rolService.modificarRol(id, rolDTO.getNombre(), rolDTO.getNombrePermiso());
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(rolActualizado)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Rol>> guardarGrupo(@Valid @RequestBody RolDTO rolDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .errors(errors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Rol rolCreado = rolService.guardarRol(rolDTO.getNombre(), rolDTO.getNombrePermiso());
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                            .data(rolCreado)
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Rol>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

}
