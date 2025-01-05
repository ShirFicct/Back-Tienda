package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Temporada;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.TemporadaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temporada")  // Prefijo 'api' para endpoints
public class TemporadaRest {

    @Autowired
    private TemporadaService temporadaService;

    // Listar todas las temporadas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Temporada>>> listarTemporadas() {
        List<Temporada> temporadas = temporadaService.listarTemporadas();
        return new ResponseEntity<>(
                ApiResponse.<List<Temporada>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(temporadas)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una temporada por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Temporada>> obtenerTemporada(@PathVariable Long id) {
        Temporada temporada = temporadaService.obtenerTemporadaPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Temporada>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(temporada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva temporada
    @PostMapping
    public ResponseEntity<ApiResponse<Temporada>> crearTemporada(@RequestBody Temporada temporada) {
        Temporada nuevaTemporada = temporadaService.crearTemporada(temporada);
        return new ResponseEntity<>(
                ApiResponse.<Temporada>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaTemporada)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una temporada
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Temporada>> actualizarTemporada(@PathVariable Long id, @RequestBody Temporada temporadaDetalles) {
        Temporada temporadaActualizada = temporadaService.modificarTemporada(id, temporadaDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Temporada>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(temporadaActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una temporada
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarTemporada(@PathVariable Long id) {
        temporadaService.eliminarTemporada(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
