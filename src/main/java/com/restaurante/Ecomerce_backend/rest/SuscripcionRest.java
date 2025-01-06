package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Suscripcion;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.SuscripcionService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suscripcion")  // Prefijo 'api' para endpoints
public class SuscripcionRest {

    @Autowired
    private SuscripcionService suscripcionService;

    // Listar todas las suscripciones
    @GetMapping
    public ResponseEntity<ApiResponse<List<Suscripcion>>> listarSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionService.listSuscripciones();
        return new ResponseEntity<>(
                ApiResponse.<List<Suscripcion>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripciones)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una suscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Suscripcion>> obtenerSuscripcion(@PathVariable Long id) {
        Suscripcion suscripcion = suscripcionService.obtSuscripcionById(id);
        return new ResponseEntity<>(
                ApiResponse.<Suscripcion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripcion)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva suscripción
    @PostMapping
    public ResponseEntity<ApiResponse<Suscripcion>> crearSuscripcion(@RequestBody Suscripcion suscripcion) {
        Suscripcion nuevaSuscripcion = suscripcionService.crearSuscripcion(suscripcion);
        return new ResponseEntity<>(
                ApiResponse.<Suscripcion>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaSuscripcion)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una suscripción
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Suscripcion>> actualizarSuscripcion(@PathVariable Long id, @RequestBody Suscripcion suscripcionDetalles) {
        Suscripcion suscripcionActualizada = suscripcionService.modificarSuscripcion(id, suscripcionDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Suscripcion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(suscripcionActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una suscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarSuscripcion(@PathVariable Long id) {
        suscripcionService.eliminarSuscripcion(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
