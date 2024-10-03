package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.SubcategoriaDTO;
import com.restaurante.Ecomerce_backend.model.Subcategoria;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.SubCategoriaService;

import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategoria")  // Prefijo 'api' para endpoints
public class SubCategoriaRest {

    @Autowired
    private SubCategoriaService subcategoriaService;

    // Listar todas las subcategorías
    @GetMapping
    public ResponseEntity<ApiResponse<List<Subcategoria>>> listarSubcategorias() {
        List<Subcategoria> subcategorias = subcategoriaService.listSubCategorias();
        return new ResponseEntity<>(
                ApiResponse.<List<Subcategoria>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(subcategorias)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una subcategoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Subcategoria>> obtenerSubcategoria(@PathVariable Long id) {
        Subcategoria subcategoria = subcategoriaService.obtenerSubCatId(id);
        return new ResponseEntity<>(
                ApiResponse.<Subcategoria>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(subcategoria)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva subcategoría
    @PostMapping
    public ResponseEntity<ApiResponse<Subcategoria>> crearSubcategoria(@RequestBody SubcategoriaDTO subcategoria) {
        Subcategoria nuevaSubcategoria = subcategoriaService.crearSubCat(subcategoria);
        return new ResponseEntity<>(
                ApiResponse.<Subcategoria>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaSubcategoria)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una subcategoría
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Subcategoria>> actualizarSubcategoria(@PathVariable Long id, @RequestBody SubcategoriaDTO subcategoriaDetalles) {
        Subcategoria subcategoriaActualizada = subcategoriaService.actualizarSubCat(id, subcategoriaDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Subcategoria>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(subcategoriaActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una subcategoría
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarSubcategoria(@PathVariable Long id) {
        subcategoriaService.eliminarSubCat(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
