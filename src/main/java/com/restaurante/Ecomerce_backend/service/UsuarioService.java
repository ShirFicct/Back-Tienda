package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.repositorios.RolRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired  //permite extraer datos de la BD
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario c) {
        return usuarioRepository.save(c);
    }

    // Obtener usuario por su ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    // Obtener usuarios por Rol
    public List<Usuario> findByRol(Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return usuarioRepository.findByRol(rol);
    }
}
