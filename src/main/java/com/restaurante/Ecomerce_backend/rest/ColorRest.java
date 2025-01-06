package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Color;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.ColorService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/color") // Prefijo 'api' para endpoints
public class ColorRest {

    @Autowired
    private ColorService colorService;

    // Listar todos los colores
    @GetMapping
    public ResponseEntity<ApiResponse<List<Color>>> listarColores() {
        List<Color> colores = colorService.listarColores();
        return new ResponseEntity<>(
                ApiResponse.<List<Color>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(colores)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener un color por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Color>> obtenerColor(@PathVariable Long id) {
        Color color = colorService.obtenerColorPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Color>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(color)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear un nuevo color
    @PostMapping
    public ResponseEntity<ApiResponse<Color>> crearColor(@RequestBody Color color) {
        Color nuevoColor = colorService.crearColor(color);
        return new ResponseEntity<>(
                ApiResponse.<Color>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoColor)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar un color existente
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Color>> actualizarColor(@PathVariable Long id, @RequestBody Color colorDetalles) {
        Color colorExistente = colorService.obtenerColorPorId(id);
        colorExistente.setNombre(colorDetalles.getNombre());
        Color colorActualizado = colorService.actualizarColor(colorExistente);
        return new ResponseEntity<>(
                ApiResponse.<Color>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(colorActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar (desactivar) un color
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Color>> eliminarColor(@PathVariable Long id) {
        Color colorDesactivado = colorService.eliminarColor(id);
        return new ResponseEntity<>(
                ApiResponse.<Color>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(colorDesactivado)
                        .build(),
                HttpStatus.OK
        );
    }
}
