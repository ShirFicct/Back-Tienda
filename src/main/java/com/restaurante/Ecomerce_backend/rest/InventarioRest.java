package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Inventario;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.InventarioService;
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
        List<Inventario> inventarios = inventarioService.listInventarios();
        return new ResponseEntity<>(
                ApiResponse.<List<Inventario>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(inventarios)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Inventario>> agregarInventario(@RequestBody Inventario inventario) {
        Inventario nuevoInventario = inventarioService.saveInventario(inventario);
        return new ResponseEntity<>(
                ApiResponse.<Inventario>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoInventario)
                        .build(),
                HttpStatus.CREATED
        );
    }
}
