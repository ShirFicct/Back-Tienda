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
    //se gestionan las operaciones lógicas y el uso de los repositorios para acceder a los datos.

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
    public List<Usuario> findByRol(Long Id) {
        Rol rol = rolRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return usuarioRepository.findByRol(rol);
    }
    // Método para asignar un rol a un usuario
    public Usuario asignarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
