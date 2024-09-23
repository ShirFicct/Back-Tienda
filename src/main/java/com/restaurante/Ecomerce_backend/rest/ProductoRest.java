package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.ProductoService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoRest {
    @Autowired
    private ProductoService productoService;
    // Listar todos los productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> listarProductos() {
        List<Producto> productos = productoService.listProductos();
        return new ResponseEntity<>(
                ApiResponse.<List<Producto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productos)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> obtenerProducto(@PathVariable Long id) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id);
            return new ResponseEntity<>(
                    ApiResponse.<Producto>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(producto)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Producto>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return new ResponseEntity<>(
                ApiResponse.<Producto>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoProducto)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar un producto
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Producto>> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, productoDetalles);
            return new ResponseEntity<>(
                    ApiResponse.<Producto>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                            .data(productoActualizado)
                            .build(),
                    HttpStatus.OK
            );
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(
                    ApiResponse.<Producto>builder()
                            .statusCode(e.getStatusCode().value())
                            .message(e.getReason())
                            .build(),
                    e.getStatusCode()
            );
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
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
