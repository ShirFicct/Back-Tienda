package com.restaurante.Ecomerce_backend.service;


import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.repositorios.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    // Obtener todos los roles
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    // Guardar un nuevo rol
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    // Obtener un rol por su ID
    public Rol findById(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }
}
