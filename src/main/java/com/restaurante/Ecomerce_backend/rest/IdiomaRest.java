package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Idioma;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.IdiomaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idioma")  // Prefijo 'api' para los endpoints
public class IdiomaRest {

    @Autowired
    private IdiomaService idiomaService;

    // Listar todos los idiomas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Idioma>>> listarIdiomas() {
        List<Idioma> idiomas = idiomaService.listarIdioma();
        return new ResponseEntity<>(
                ApiResponse.<List<Idioma>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(idiomas)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener un idioma por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Idioma>> obtenerIdioma(@PathVariable Long id) {
        Idioma idioma = idiomaService.obtenerIdiomaPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Idioma>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(idioma)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear un nuevo idioma
    @PostMapping
    public ResponseEntity<ApiResponse<Idioma>> crearIdioma(@RequestBody Idioma idioma) {
        Idioma nuevoIdioma = idiomaService.crearIdioma(idioma);
        return new ResponseEntity<>(
                ApiResponse.<Idioma>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoIdioma)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar un idioma
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Idioma>> actualizarIdioma(@PathVariable Long id, @RequestBody Idioma idiomaDetalles) {
        Idioma idiomaActualizado = idiomaService.actualizarIdioma(id, idiomaDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Idioma>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(idiomaActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar un idioma
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarIdioma(@PathVariable Long id) {
        idiomaService.eliminarIdioma(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
