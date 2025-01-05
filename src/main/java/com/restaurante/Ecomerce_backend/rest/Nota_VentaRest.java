package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.Nota_VentaDTO;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Factura>>> listarNotas() {
        List<Factura> nota = nota_ventaService.listNota_venta();
        return new ResponseEntity<>(
                ApiResponse.<List<Factura>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(nota)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Factura>> obtenerNota(@PathVariable Long id) {
        Factura notaVenta = nota_ventaService.obtenerNotaPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Factura>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(notaVenta)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Factura>> crearNota(@RequestBody Nota_VentaDTO nota_venta) {
        Factura nuevoNota = nota_ventaService.crearNota(nota_venta);
        return new ResponseEntity<>(
                ApiResponse.<Factura>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoNota)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Factura>> actualizarNota(@PathVariable Long id, @RequestBody Nota_VentaDTO nota_venta) {
        Factura notaActualizado = nota_ventaService.actualizarNota(id,nota_venta);
        return new ResponseEntity<>(
                ApiResponse.<Factura>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(notaActualizado)
                        .build(),
                HttpStatus.OK
        );
    }
}
