package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.PedidoDTO;
import com.restaurante.Ecomerce_backend.model.Pedido;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.PedidoService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoRest {

    @Autowired
    private PedidoService pedidoService;

    // Listar todos los pedidos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listPedido();
        return new ResponseEntity<>(
                ApiResponse.<List<Pedido>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(pedidos)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{pedidoId}/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(@PathVariable Long pedidoId) {
        Pedido pedidoConfirmado = pedidoService.confirmarPedido(pedidoId);
        return ResponseEntity.ok(pedidoConfirmado);
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Pedido>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(pedido)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<ApiResponse<Pedido>> crearPedido(@RequestBody PedidoDTO pedido) {
        Pedido nuevoPedido = pedidoService.crearPedido(pedido);
        return new ResponseEntity<>(
                ApiResponse.<Pedido>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoPedido)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar un pedido
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> actualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDetalles) {
        Pedido pedidoActualizado = pedidoService.modificarPedido(id, pedidoDetalles);
        return new ResponseEntity<>(
                ApiResponse.<Pedido>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(pedidoActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.NO_CONTENT))
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}
