package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.PromocionDTO;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productopromo")  // Prefijo 'api' para seguir la convención
public class ProductoPromoRest {

    @Autowired
    private ProdPromoService prodPromoService;

    // Listar todas las promociones de productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoPromo>>> listarProductoPromos() {
        List<ProductoPromo> productoPromos = prodPromoService.listarProductosPromocionales();
        return new ResponseEntity<>(
                ApiResponse.<List<ProductoPromo>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoPromos)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una promoción de producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoPromo>> obtenerProductoPromo(@PathVariable Long id) {
        ProductoPromo productoPromo = prodPromoService.obtenerProductoPromoPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<ProductoPromo>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoPromo)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva promoción de producto
    @PostMapping
    public ResponseEntity<ApiResponse<ProductoPromo>> crearProductoPromo(@RequestBody PromocionDTO promocionDTO) {
        ProductoPromo nuevoProductoPromo = prodPromoService.crearProductoPromo(promocionDTO);
        return new ResponseEntity<>(
                ApiResponse.<ProductoPromo>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoProductoPromo)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una promoción de producto
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoPromo>> actualizarProductoPromo(@PathVariable Long id, @RequestBody PromocionDTO promocionDTO) {
        ProductoPromo productoPromoActualizado = prodPromoService.modificarProductoPromo(id, promocionDTO);
        return new ResponseEntity<>(
                ApiResponse.<ProductoPromo>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoPromoActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una promoción de producto
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProductoPromo(@PathVariable Long id) {
        prodPromoService.eliminarProductoPromo(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
