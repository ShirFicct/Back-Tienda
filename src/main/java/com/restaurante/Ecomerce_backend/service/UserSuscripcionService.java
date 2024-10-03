package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.UserSuscripDTO;
import com.restaurante.Ecomerce_backend.model.UserSuscripcion;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.model.Suscripcion;
import com.restaurante.Ecomerce_backend.repositorios.UserSuscripcionRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;
import com.restaurante.Ecomerce_backend.repositorios.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserSuscripcionService {

    @Autowired
    private UserSuscripcionRepository userSuscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    // Listar todas las suscripciones de usuario
    public List<UserSuscripcion> listarUserSuscripciones() {
        return userSuscripcionRepository.findAll();
    }

    // Obtener una suscripción de usuario por ID
    public UserSuscripcion obtenerUserSuscripPorId(Long id) {
        return userSuscripcionRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripción de usuario no encontrada"));
    }

    // Crear una nueva suscripción de usuario
    public UserSuscripcion crearUserSuscrip(UserSuscripDTO userSuscripDTO) {
        if (userSuscripDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Suscripción de usuario no puede ser nula");
        }

        Usuario usuario = usuarioRepository.findById(userSuscripDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        Suscripcion suscripcion = suscripcionRepository.findById(userSuscripDTO.getIdSuscripcion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Suscripción no encontrada"));

        UserSuscripcion userSuscripcion = new UserSuscripcion();
        userSuscripcion.setUsuario(usuario);
        userSuscripcion.setSuscripcion(suscripcion);
        userSuscripcion.setFechaInicio(userSuscripDTO.getFechaInicio());
        userSuscripcion.setFechaFin(userSuscripDTO.getFechaFin());
        userSuscripcion.setEstado(userSuscripDTO.isEstado());

        return userSuscripcionRepository.save(userSuscripcion);
    }

    // Modificar una suscripción de usuario
    public UserSuscripcion modificarUserSuscrip(Long id, UserSuscripDTO userSuscripDTO) {
        UserSuscripcion userSuscripcionExistente = obtenerUserSuscripPorId(id);
        if (userSuscripcionExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripción de usuario no encontrada");
        }

        Usuario usuario = usuarioRepository.findById(userSuscripDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Suscripcion suscripcion = suscripcionRepository.findById(userSuscripDTO.getIdSuscripcion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Suscripción no encontrada"));

        userSuscripcionExistente.setUsuario(usuario);
        userSuscripcionExistente.setSuscripcion(suscripcion);
        userSuscripcionExistente.setFechaInicio(userSuscripDTO.getFechaInicio());
        userSuscripcionExistente.setFechaFin(userSuscripDTO.getFechaFin());
        userSuscripcionExistente.setEstado(userSuscripDTO.isEstado());

        return userSuscripcionRepository.save(userSuscripcionExistente);
    }

    // Eliminar una suscripción de usuario
    public void eliminarUserSuscrip(Long id) {
        UserSuscripcion userSuscripcion = obtenerUserSuscripPorId(id);
        userSuscripcionRepository.delete(userSuscripcion);
    }
}
