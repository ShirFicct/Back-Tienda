package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.ProductoResDTO;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productoreserva")  // Prefijo 'api' para seguir la convención
public class ProdReservaRest {

    @Autowired
    private ProdReservaService prodReservaService;

    // Listar todas las reservas de productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoReseva>>> listarProductoReservas() {
        List<ProductoReseva> productoReservas = prodReservaService.listarProductosReserva();
        return new ResponseEntity<>(
                ApiResponse.<List<ProductoReseva>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoReservas)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una reserva de producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoReseva>> obtenerProductoReserva(@PathVariable Long id) {
        ProductoReseva productoReserva = prodReservaService.obtenerProductoResPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<ProductoReseva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoReserva)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva reserva de producto
    @PostMapping
    public ResponseEntity<ApiResponse<ProductoReseva>> crearProductoReserva(@RequestBody ProductoResDTO productoResDTO) {
        ProductoReseva nuevaProductoReserva = prodReservaService.crearProductoRes(productoResDTO);
        return new ResponseEntity<>(
                ApiResponse.<ProductoReseva>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaProductoReserva)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una reserva de producto
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoReseva>> actualizarProductoReserva(@PathVariable Long id, @RequestBody ProductoResDTO productoResDTO) {
        ProductoReseva productoReservaActualizado = prodReservaService.modificarProductoRes(id, productoResDTO);
        return new ResponseEntity<>(
                ApiResponse.<ProductoReseva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoReservaActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una reserva de producto
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProductoReserva(@PathVariable Long id) {
        prodReservaService.eliminarProductoRes(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}

