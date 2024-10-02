package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Nota_venta;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.Nota_VentaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nota_venta")
public class Nota_VentaRest {

    @Autowired
    private Nota_VentaService nota_ventaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Nota_venta>>> listarNotas() {
        List<Nota_venta> nota = nota_ventaService.listNota_venta();
        return new ResponseEntity<>(
                ApiResponse.<List<Nota_venta>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(nota)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Nota_venta>> obtenerNota(@PathVariable Long id) {
        Nota_venta notaVenta = nota_ventaService.obtenerNotaPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Nota_venta>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(notaVenta)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Nota_venta>> crearNota(@RequestBody Nota_venta nota_venta) {
        Nota_venta nuevoNota = nota_ventaService.crearNota(nota_venta);
        return new ResponseEntity<>(
                ApiResponse.<Nota_venta>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoNota)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Nota_venta>> actualizarNota(@PathVariable Long id, @RequestBody Nota_venta nota_venta) {
        Nota_venta notaActualizado = nota_ventaService.actualizarNota(id,nota_venta);
        return new ResponseEntity<>(
                ApiResponse.<Nota_venta>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(notaActualizado)
                        .build(),
                HttpStatus.OK
        );
    }
}
