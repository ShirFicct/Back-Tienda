package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.ReservaDTO;
import com.restaurante.Ecomerce_backend.model.Reserva;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.ReservaService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaRest {

    @Autowired
    private ReservaService reservaService;

    // Listar todas las reservas
    @GetMapping
    public ResponseEntity<ApiResponse<List<Reserva>>> listarReservas() {
        List<Reserva> reservas = reservaService.listarReservas();
        return new ResponseEntity<>(
                ApiResponse.<List<Reserva>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reservas)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Reserva>> obtenerReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.obtenerReservaPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Reserva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reserva)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<ApiResponse<Reserva>> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva nuevaReserva = reservaService.crearReserva(reservaDTO);
        return new ResponseEntity<>(
                ApiResponse.<Reserva>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevaReserva)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar una reserva
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Reserva>> actualizarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        Reserva reservaActualizada = reservaService.modificarReserva(id, reservaDTO);
        return new ResponseEntity<>(
                ApiResponse.<Reserva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reservaActualizada)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

    @PatchMapping("/{reservaId}/completar-pago")
    public ResponseEntity<ApiResponse<Reserva>> completarPago(
            @PathVariable Long reservaId,
            @RequestParam float montoAdicional
    ) {
        Reserva reservaCompletada = reservaService.completarPagoReserva(reservaId, montoAdicional);
        return new ResponseEntity<>(
                ApiResponse.<Reserva>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reservaCompletada)
                        .build(),
                HttpStatus.OK
        );
    }
    @PatchMapping("/cancelar-expiradas")
    public ResponseEntity<ApiResponse<String>> cancelarReservasExpiradas() {
        reservaService.cancelarReservasExpiradas();
        return new ResponseEntity<>(
                ApiResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data("Reservas expiradas fueron canceladas exitosamente.")
                        .build(),
                HttpStatus.OK
        );
    }
}
