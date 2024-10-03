package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ReporteDTO;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.model.Reporte_Sucursal;
import com.restaurante.Ecomerce_backend.model.Sucursal;
import com.restaurante.Ecomerce_backend.repositorios.ProductoRepository;
import com.restaurante.Ecomerce_backend.repositorios.ReporteRepository;
import com.restaurante.Ecomerce_backend.repositorios.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporte;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte_Sucursal> listReporte() {
        return reporteRepository.findAll();
    }

    public Reporte_Sucursal obtReportePorId(Long id) {
        return reporteRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado"));
    }

    public Reporte_Sucursal crearReporte(ReporteDTO reporteSucursal) {
       if(reporteSucursal==null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reporte no encontrado");
       }

        Producto producto = productoService.obtenerProductoPorId(reporteSucursal.getIdProducto());
        if(producto==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado");
        }

        Sucursal sucursal= sucursalService.obtSucursal(reporteSucursal.getIdSucursal());
        if(sucursal==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sucursal no encontrado");
        }

        Reporte_Sucursal reporte = new Reporte_Sucursal();
        reporte.setProducto(producto);
        reporte.setSucursal(sucursal);
        reporte.setFechaInicio(reporteSucursal.getFechaInicio());
        reporte.setFechaFinal(reporteSucursal.getFechaFinal());
        return reporteRepository.save(reporte);

    }

    public Reporte_Sucursal actualizarReporte(Long id, ReporteDTO reporteSucursal) {
        Reporte_Sucursal reporteSucursal1 = obtReportePorId(id);
        if (reporteSucursal1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado");
        }

        Producto prod=productoRepository.findById(reporteSucursal.getIdProducto())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "El prodcuto no fue encontrado"));

Sucursal sucursal=sucursalRepository.findById(reporteSucursal.getIdSucursal())
        .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal no fue encontrado"));

        reporteSucursal1.setProducto(prod);
        reporteSucursal1.setSucursal(sucursal);
        reporteSucursal1.setFechaInicio(reporteSucursal.getFechaInicio());
        reporteSucursal1.setFechaFinal(reporteSucursal.getFechaFinal());
        return reporteRepository.save(reporteSucursal1);
    }

public void eliminarReporte(Long id) {
        Reporte_Sucursal reporteSucursal = obtReportePorId(id);
        reporteRepository.delete(reporteSucursal);
}

}
