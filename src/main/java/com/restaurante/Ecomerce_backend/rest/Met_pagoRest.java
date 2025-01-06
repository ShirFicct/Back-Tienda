package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Met_Pago;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.Met_pagoService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodopago")
public class Met_pagoRest {

    @Autowired
    private Met_pagoService met_pagoService;

    // Listar todos los métodos de pago
    @GetMapping
    public ResponseEntity<ApiResponse<List<Met_Pago>>> listarMetodos() {
        List<Met_Pago> metodos = met_pagoService.ListMetodos();
        return new ResponseEntity<>(ApiResponse.<List<Met_Pago>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                .data(metodos)
                .build(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Met_Pago>> obtenerMetodo(@PathVariable Long id) {
        Met_Pago metodo = met_pagoService.obtMetoPorId(id);
        return new ResponseEntity<>(ApiResponse.<Met_Pago>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                .data(metodo)
                .build(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Met_Pago>> crearMetodo(@RequestBody Met_Pago met_pago) {
        Met_Pago nuevoMetodo = met_pagoService.crearMetPago(met_pago);
        return new ResponseEntity<>(ApiResponse.<Met_Pago>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                .data(nuevoMetodo)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Met_Pago>> actualizarMetodo(@PathVariable Long id, @RequestBody Met_Pago met_pagoDetalles) {
        Met_Pago metodoActualizado = met_pagoService.actualizarMetPago(id, met_pagoDetalles);
        return new ResponseEntity<>(ApiResponse.<Met_Pago>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                .data(metodoActualizado)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarMetodo(@PathVariable Long id) {
        met_pagoService.eliminarMetPago(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                .build(), HttpStatus.NO_CONTENT);
    }
}
