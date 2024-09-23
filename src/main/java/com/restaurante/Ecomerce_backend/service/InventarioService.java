package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Inventario;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.model.Sucursal;
import com.restaurante.Ecomerce_backend.repositorios.InventarioRepository;
import com.restaurante.Ecomerce_backend.repositorios.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listInventarios() {
        return inventarioRepository.findAll();
    }

    public Inventario obtInventario(Long id) {
        return inventarioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));
    }

    public Inventario crearInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);

    }

    public Inventario saveInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }



}
