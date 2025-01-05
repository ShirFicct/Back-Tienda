package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Idioma;
import com.restaurante.Ecomerce_backend.model.Marca;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.MarcaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.DoubleStream.builder;

@RestController
@RequestMapping("/marca")
public class MarcaRest {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Marca>>> listarMarcas() {
        List<Marca> marcas = marcaService.listarMarcas();
        return new ResponseEntity<>(
                ApiResponse.<List<Marca>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(marcas)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una marca por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Marca>> obtMarca(@PathVariable Long id) {
        Marca marca = marcaService.obtMarcaById(id);
        return new ResponseEntity<>(
                ApiResponse.<Marca>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(marca)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva marca
    @PostMapping
    public ResponseEntity<ApiResponse<Marca>> crearMarca(@RequestBody Marca marca) {
        Marca newMarca = marcaService.createMarca(marca);
        return new ResponseEntity<>(
                ApiResponse.<Marca>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(newMarca)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una marca existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Marca>> updateMarca(@PathVariable Long id, @RequestBody Marca marcaDetails) {
        Marca updatedMarca = marcaService.updateMarca(id, marcaDetails);
        return new ResponseEntity<>(
                ApiResponse.<Marca>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(updatedMarca)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una marca por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMarca(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
