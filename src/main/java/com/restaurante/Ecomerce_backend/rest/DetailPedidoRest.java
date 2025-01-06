package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.DetallePedidoDTO;
import com.restaurante.Ecomerce_backend.model.Detalle_Pedido;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.DetailPedidoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido/detalle-pedido")
public class DetailPedidoRest {
    @Autowired
    private DetailPedidoService detailPedidoService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Detalle_Pedido>>> listarDetallesPedido() {
        List<Detalle_Pedido> detalles = detailPedidoService.findAll();
        return new ResponseEntity<>(
                ApiResponse.<List<Detalle_Pedido>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_Pedido>> obtenerDetallePorId(@PathVariable Long id) {
        Detalle_Pedido detalle = detailPedidoService.findById(id);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_Pedido>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalle)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<ApiResponse<List<Detalle_Pedido>>> listarDetallesPorPedido(@PathVariable Long pedidoId) {
        List<Detalle_Pedido> detalles = detailPedidoService.obtByPedido(pedidoId);
        return new ResponseEntity<>(
                ApiResponse.<List<Detalle_Pedido>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalles)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Detalle_Pedido>> crearDetallePedido(@RequestBody DetallePedidoDTO dto) {
        Detalle_Pedido detalleCreado = detailPedidoService.crear(dto);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_Pedido>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(detalleCreado)
                        .build(),
                HttpStatus.CREATED
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalle_Pedido>> actualizarDetallePedido(
            @PathVariable Long id,
            @RequestBody DetallePedidoDTO dto
    ) {
        Detalle_Pedido detalleActualizado = detailPedidoService.actualizar(id, dto);
        return new ResponseEntity<>(
                ApiResponse.<Detalle_Pedido>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(detalleActualizado)
                        .build(),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDetallePedido(@PathVariable Long id) {
        detailPedidoService.eliminar(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}

