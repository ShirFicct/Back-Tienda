package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Nota_venta;
import com.restaurante.Ecomerce_backend.repositorios.Nota_VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class Nota_VentaService {
    @Autowired
    private Nota_VentaRepository nota_ventaRepository;

    public List<Nota_venta> listNota_venta() {
        return nota_ventaRepository.findAll();
    }

    public Nota_venta obtenerNotaPorId(Long id) {
        return nota_ventaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // Crear un nuevo producto
    public Nota_venta crearNota(Nota_venta nota) {
        return nota_ventaRepository.save(nota);
    }

    public Nota_venta actualizarNota(Long id, Nota_venta notaVenta) {
        Nota_venta notaVenta1= obtenerNotaPorId(notaVenta.getId());
        notaVenta1.setPedido(notaVenta.getPedido());
        notaVenta1.setProducto(notaVenta.getProducto());
        notaVenta1.setCantidad(notaVenta.getCantidad());
        return nota_ventaRepository.save(notaVenta1);
    }


}

