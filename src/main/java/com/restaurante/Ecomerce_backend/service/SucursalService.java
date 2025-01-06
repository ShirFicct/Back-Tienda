package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Sucursal;
import com.restaurante.Ecomerce_backend.repositorios.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> listSucursal() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtSucursal(Long id) {
        return sucursalRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrado"));
    }

    public Sucursal crearSucursal(Sucursal sucursal) {
        Sucursal sucursa= new Sucursal();
        sucursa.setNombre(sucursal.getNombre());
        sucursa.setDireccion( sucursal.getDireccion());
        return sucursalRepository.save(sucursal);
    }

    public Sucursal actualizarSucursal(Long id, Sucursal sucursalDetalle) {
        Sucursal sucursal = obtSucursal(sucursalDetalle.getId());
        sucursal.setNombre(sucursalDetalle.getNombre());
        sucursal.setDireccion(sucursalDetalle.getDireccion());
        return sucursalRepository.save(sucursal);
    }

    public void eliminarSucursal(Long id) {
       Sucursal sucursal = obtSucursal(id);
        sucursalRepository.delete(sucursal);
    }
}
