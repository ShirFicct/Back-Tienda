package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.ProductoDTO;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.ProductoService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto") //
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

    @GetMapping("/temporada/{temporadaId}")
    public ResponseEntity<ApiResponse<List<Producto>>> listarProductoByTemporada(@PathVariable Long id) {
        List<Producto> productos = productoService.getProductosByTemporada(id);
        return new ResponseEntity<>(
                ApiResponse.<List<Producto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productos)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/subcategoria/{subcategoriaId}")
    public ResponseEntity<ApiResponse<List<Producto>>> listarProductoBySubCategoria(@PathVariable Long id) {
        List<Producto> productos = productoService.getProductosBySubcategoria(id);
        return new ResponseEntity<>(
                ApiResponse.<List<Producto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productos)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<ApiResponse<List<Producto>>> listarProductoByMARCA(@PathVariable Long id) {
        List<Producto> productos = productoService.getProductosByMarca(id);
        return new ResponseEntity<>(
                ApiResponse.<List<Producto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productos)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/promocion/{promoId}")
    public ResponseEntity<ApiResponse<List<Producto>>> listarProductoByPROMO(@PathVariable Long id) {
        List<Producto> productos = productoService.getProductosByPromocion(id);
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
        Producto producto = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Producto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(producto)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> crearProducto(@RequestBody ProductoDTO producto) {
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
    public ResponseEntity<ApiResponse<Producto>> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDetalles) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Producto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(productoActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
