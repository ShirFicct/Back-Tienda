package com.restaurante.Ecomerce_backend.rest;
import com.restaurante.Ecomerce_backend.dto.InventarioDTO;
import com.restaurante.Ecomerce_backend.model.Inventario;
import com.restaurante.Ecomerce_backend.response.ApiResponse;

import com.restaurante.Ecomerce_backend.service.InventarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioRest {
    @Autowired
    private InventarioService inventarioService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Inventario>>> listarInventarios() {
        List<Inventario> inventarios = inventarioService.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Inventario>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))  // Ajusta si usas tu propia clase
                        .data(inventarios)
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventario>> obtenerInventarioPorId(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventario)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Inventario>> crearInventario(@RequestBody InventarioDTO dto) {
        Inventario nuevoInventario = inventarioService.crear(dto);
        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoInventario)
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventario>> actualizarInventario(@PathVariable Long id,
                                                                        @RequestBody InventarioDTO dto) {
        Inventario inventarioActualizado = inventarioService.update(id, dto);
        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventarioActualizado)
                        .build(),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventario>> eliminarInventario(@PathVariable Long id) {
        // Si quieres devolver el inventario antes de inactivarlo, puedes obtenerlo antes
        Inventario inventario = inventarioService.findById(id);
        inventarioService.delete(id);

        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventario) // Devuelves el inventario "antes" de eliminarlo lógicamente, si lo deseas
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/producto/{productoId}")
    public ResponseEntity<ApiResponse<List<Inventario>>> obtenerInventariosPorProducto(@PathVariable Long productoId) {
        List<Inventario> inventarios = inventarioService.obtenerInventariosPorProducto(productoId);
        return new ResponseEntity<>(
                ApiResponse.<List<Inventario>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventarios)
                        .build(),
                HttpStatus.OK
        );
    }

    /**
     * 7. Decrementar stock (por pago confirmado, por ejemplo)
     *    Recibe los IDs de producto, sucursal, talla, color y la cantidad a restar
     */
    @PutMapping("/decrementar-stock")
    public ResponseEntity<ApiResponse<String>> decrementarStock(
            @RequestParam Long productoId,
            @RequestParam Long almacenId,   // Sucursal
            @RequestParam Long tallaId,
            @RequestParam Long colorId,
            @RequestParam int cantidad
    ) {
        inventarioService.decrementarStockPorPagoConfirmado(productoId, almacenId, tallaId, colorId, cantidad);

        return new ResponseEntity<>(
                ApiResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data("Stock decrementado exitosamente")
                        .build(),
                HttpStatus.OK
        );
    }
}

