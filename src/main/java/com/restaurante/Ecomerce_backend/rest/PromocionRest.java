package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.PromocionDTO;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.model.Promocion;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.PromocionService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promocion")  // Prefijo 'api' para los endpoints
public class PromocionRest {

    @Autowired
    private PromocionService promocionService;

    // Listar todas las promociones
    @GetMapping
    public ResponseEntity<ApiResponse<List<Promocion>>> listarPromociones() {
        List<Promocion> promociones = promocionService.listPromo();
        return new ResponseEntity<>(
                ApiResponse.<List<Promocion>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(promociones)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una promoción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Promocion>> obtenerPromocion(@PathVariable Long id) {
        Promocion promocion = promocionService.obtPromocionId(id);
        return new ResponseEntity<>(
                ApiResponse.<Promocion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(promocion)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/suscripcion/{suscripcionId}")
    public ResponseEntity<ApiResponse<List<Promocion>>> listarProductoBySuscripcion(@PathVariable Long id) {
        List<Promocion> promocions = promocionService.getPromoBySuscr(id);
        return new ResponseEntity<>(
                ApiResponse.<List<Promocion>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(promocions)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva promoción
    @PostMapping
    public ResponseEntity<ApiResponse<Promocion>> crearPromocion(@RequestBody PromocionDTO promocion) {
        Promocion nuevaPromocion = promocionService.crearPromocion(promocion);
        return new ResponseEntity<>(
                ApiResponse.<Promocion>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaPromocion)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una promoción
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Promocion>> actualizarPromocion(@PathVariable Long id, @RequestBody PromocionDTO promocionDetalles) {
        Promocion promocionActualizada = promocionService.actualizarPromocion(id, promocionDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Promocion>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(promocionActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una promoción
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
