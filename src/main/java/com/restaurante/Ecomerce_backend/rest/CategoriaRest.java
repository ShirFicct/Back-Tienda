package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Categoria;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.CategoriaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaRest {
    @Autowired
    private CategoriaService categoriaService;

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listCategoria();
        return new ResponseEntity<>(
                ApiResponse.<List<Categoria>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(categorias)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> obtenerCategoria(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.obtenerCatId(id);
            return new ResponseEntity<>(
                    ApiResponse.<Categoria>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(categoria)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Categoria>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>> crearCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaNueva = categoriaService.crearCat(categoria);
        return new ResponseEntity<>(
                ApiResponse.<Categoria>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(categoriaNueva)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetalles) {
        try {
            Categoria categoriaActualizada = categoriaService.actualizarCat(id, categoriaDetalles);
            return new ResponseEntity<>(
                    ApiResponse.<Categoria>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(categoriaActualizada)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Categoria>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminarCat(id);
            return new ResponseEntity<>(
                    ApiResponse.<Void>builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                            .build(),
                    HttpStatus.NO_CONTENT
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Void>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }
}
