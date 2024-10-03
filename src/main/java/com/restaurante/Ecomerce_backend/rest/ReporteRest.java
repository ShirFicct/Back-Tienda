package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.dto.ReporteDTO;
import com.restaurante.Ecomerce_backend.model.Reporte_Sucursal;
import com.restaurante.Ecomerce_backend.response.ApiResponse;
import com.restaurante.Ecomerce_backend.service.ReporteService;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reporte")
public class ReporteRest {

    @Autowired
    private ReporteService reporteService;

    // Listar todos los reportes de sucursales
    @GetMapping
    public ResponseEntity<ApiResponse<List<Reporte_Sucursal>>> listarReporte() {
        List<Reporte_Sucursal> reporteSucursals = reporteService.listReporte();
        return new ResponseEntity<>(
                ApiResponse.<List<Reporte_Sucursal>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reporteSucursals)
                        .build(),
                HttpStatus.OK
        );
    }

    // Obtener un reporte de sucursal por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Reporte_Sucursal>> obtenerReportePorId(@PathVariable Long id) {
        Reporte_Sucursal reporteSucursal = reporteService.obtReportePorId(id);
        return new ResponseEntity<>(
                ApiResponse.<Reporte_Sucursal>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reporteSucursal)
                        .build(),
                HttpStatus.OK
        );
    }

    // Crear un nuevo reporte de sucursal
    @PostMapping
    public ResponseEntity<ApiResponse<Reporte_Sucursal>> agregarReporte(@RequestBody ReporteDTO reporteDTO) {
        Reporte_Sucursal nuevoReporteSucursal = reporteService.crearReporte(reporteDTO);
        return new ResponseEntity<>(
                ApiResponse.<Reporte_Sucursal>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.CREATED))
                        .data(nuevoReporteSucursal)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Actualizar un reporte de sucursal
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Reporte_Sucursal>> actualizarReporte(@PathVariable Long id, @RequestBody ReporteDTO reporteDTO) {
        Reporte_Sucursal reporteActualizado = reporteService.actualizarReporte(id, reporteDTO);
        return new ResponseEntity<>(
                ApiResponse.<Reporte_Sucursal>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(HttpStatusMessage.getMessage(HttpStatus.OK))
                        .data(reporteActualizado)
                        .build(),
                HttpStatus.OK
        );
    }

}
