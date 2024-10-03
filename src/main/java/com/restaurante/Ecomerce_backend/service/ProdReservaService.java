package com.restaurante.Ecomerce_backend.service;



import com.restaurante.Ecomerce_backend.dto.ProductoResDTO;
import com.restaurante.Ecomerce_backend.model.ProductoReseva;
import com.restaurante.Ecomerce_backend.model.ProductoReseva;
import com.restaurante.Ecomerce_backend.model.Reserva;
import com.restaurante.Ecomerce_backend.repositorios.ProductoReservaRepository;
import com.restaurante.Ecomerce_backend.repositorios.ProductoReservaRepository;
import com.restaurante.Ecomerce_backend.repositorios.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdReservaService {

    @Autowired
    private ProductoReservaRepository productoReservaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // Listar todos los productos de reserva
    public List<ProductoReseva> listarProductosReserva() {
        return productoReservaRepository.findAll();
    }

    // Obtener un producto de reserva por ID
    public ProductoReseva obtenerProductoResPorId(Long id) {
        return productoReservaRepository
                .findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto de reserva no encontrado"));
    }

    // Crear un nuevo producto de reserva
    public ProductoReseva crearProductoRes(ProductoResDTO productoResDTO) {
        if (productoResDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto de reserva no puede ser nulo");
        }

        Reserva reserva = reservaRepository.findById(productoResDTO.getIdReserva())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva no encontrada"));

        ProductoReseva productoRes = new ProductoReseva();
        productoRes.setId(productoResDTO.getCodProd());
        productoRes.setReserva(reserva);
        productoRes.setCantidad(productoResDTO.getCantidad());

        return productoReservaRepository.save(productoRes);
    }

    // Modificar un producto de reserva
    public ProductoReseva modificarProductoRes(Long id, ProductoResDTO productoResDTO) {
        ProductoReseva productoResExistente = obtenerProductoResPorId(id);
        if (productoResExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto de reserva no encontrado");
        }

        Reserva reserva = reservaRepository.findById(productoResDTO.getIdReserva())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        productoResExistente.setId(productoResDTO.getCodProd());
        productoResExistente.setReserva(reserva);
        productoResExistente.setCantidad(productoResDTO.getCantidad());

        return productoReservaRepository.save(productoResExistente);
    }

    // Eliminar un producto de reserva
    public void eliminarProductoRes(Long id) {
        ProductoReseva productoRes = obtenerProductoResPorId(id);
        productoReservaRepository.delete(productoRes);
    }
}

