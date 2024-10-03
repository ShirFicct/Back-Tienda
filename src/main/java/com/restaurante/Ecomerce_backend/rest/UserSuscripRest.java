package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.UserSuscripDTO;
import com.restaurante.Ecomerce_backend.model.UserSuscripcion;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.UserSuscripcionService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersuscripcion")
public class UserSuscripRest {

    @Autowired
    private UserSuscripcionService userSuscripService;

    // Listar todas las suscripciones de usuarios
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserSuscripcion>>> listarUserSuscripciones() {
        List<UserSuscripcion> suscripciones = userSuscripService.listarUserSuscripciones();
        return new ResponseEntity<>(
                ApiResponse.<List<UserSuscripcion>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripciones)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una suscripción de usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSuscripcion>> obtenerUserSuscripcion(@PathVariable Long id) {
        UserSuscripcion suscripcion = userSuscripService.obtenerUserSuscripPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<UserSuscripcion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripcion)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva suscripción de usuario
    @PostMapping
    public ResponseEntity<ApiResponse<UserSuscripcion>> crearUserSuscripcion(@RequestBody UserSuscripDTO userSuscripDTO) {
        UserSuscripcion nuevaSuscripcion = userSuscripService.crearUserSuscrip(userSuscripDTO);
        return new ResponseEntity<>(
                ApiResponse.<UserSuscripcion>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaSuscripcion)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una suscripción de usuario
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSuscripcion>> actualizarUserSuscripcion(@PathVariable Long id, @RequestBody UserSuscripDTO userSuscripDTO) {
        UserSuscripcion suscripcionActualizada = userSuscripService.modificarUserSuscrip(id, userSuscripDTO);
        return new ResponseEntity<>(
                ApiResponse.<UserSuscripcion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripcionActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una suscripción de usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarUserSuscripcion(@PathVariable Long id) {
        userSuscripService.eliminarUserSuscrip(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
