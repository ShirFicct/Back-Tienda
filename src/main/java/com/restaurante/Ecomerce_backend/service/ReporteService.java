package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Reporte_Sucursal;
import com.restaurante.Ecomerce_backend.repositorios.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte_Sucursal> listReporte() {
        return reporteRepository.findAll();
    }

    public Reporte_Sucursal obtReporte(Long id) {
        return reporteRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado"));
    }

    public Reporte_Sucursal crearReporte(Reporte_Sucursal reporteSucursal) {
       if(reporteSucursal==null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reporte no encontrado");
       }
        return reporteRepository.save(reporteSucursal);

    }

    public Reporte_Sucursal actualizarReporte(Long id,Reporte_Sucursal reporteSucursal) {
        Reporte_Sucursal reporteSucursal1 = obtReporte(id);
        reporteSucursal1.setNro(reporteSucursal.getNro());
        reporteSucursal1.setSucursal(reporteSucursal.getSucursal());
        reporteSucursal1.setProducto(reporteSucursal.getProducto());
        reporteSucursal1.setFechaInicio(reporteSucursal.getFechaInicio());
        reporteSucursal1.setFechaFinal(reporteSucursal.getFechaFinal());
        return reporteRepository.save(reporteSucursal);
    }

public void eliminarReporte(Long id) {
        Reporte_Sucursal reporteSucursal = obtReporte(id);
        reporteRepository.delete(reporteSucursal);
}

}
