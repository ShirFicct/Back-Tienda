package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Sucursal;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.SucursalService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursal")  // Prefijo 'api' para endpoints
public class SucursalRest {

    @Autowired
    private SucursalService sucursalService;

    // Listar todas las sucursales
    @GetMapping
    public ResponseEntity<ApiResponse<List<Sucursal>>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.listSucursal();
        return new ResponseEntity<>(
                ApiResponse.<List<Sucursal>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sucursales)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una sucursal por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sucursal>> obtenerSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.obtSucursal(id);
        return new ResponseEntity<>(
                ApiResponse.<Sucursal>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sucursal)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva sucursal
    @PostMapping
    public ResponseEntity<ApiResponse<Sucursal>> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.crearSucursal(sucursal);
        return new ResponseEntity<>(
                ApiResponse.<Sucursal>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaSucursal)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una sucursal
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Sucursal>> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursalDetalles) {
        Sucursal sucursalActualizada = sucursalService.actualizarSucursal(id, sucursalDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Sucursal>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(sucursalActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    /*// Eliminar una sucursal
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }*/
}
