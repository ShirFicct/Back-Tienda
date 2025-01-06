package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.DetalleReservaDTO;
import com.restaurante.Ecomerce_backend.model.Detalle_reseva;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.DetalleReservaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva/detalle-reserva")

public class DetailReservaRest {
    @Autowired
    private DetalleReservaService detalleReservaService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Detalle_reseva>>> listarDetallesReserva() {
        List<Detalle_reseva> detalles = detalleReservaService.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Detalle_reseva>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_reseva>> obtenerDetalleReserva(@PathVariable Long id) {
        Detalle_reseva detalle = detalleReservaService.findById(id);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_reseva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalle)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Detalle_reseva>> crearDetalleReserva(@RequestBody DetalleReservaDTO dto) {
        Detalle_reseva detalleCreado = detalleReservaService.crear(dto);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_reseva>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(detalleCreado)
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_reseva>> actualizarDetalleReserva(
            @PathVariable Long id,
            @RequestBody DetalleReservaDTO dto
    ) {
        Detalle_reseva detalleActualizado = detalleReservaService.actualizar(id, dto);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_reseva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalleActualizado)
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<List<Detalle_reseva>>> listarDetallesPorReserva(@PathVariable Long reservaId) {
        List<Detalle_reseva> detalles = detalleReservaService.findByReservaId(reservaId);
        return new ResponseEntity<>(
                ApiResponse.<List<Detalle_reseva>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDetalleReserva(@PathVariable Long id) {
        detalleReservaService.eliminar(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

}
