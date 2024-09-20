package com.restaurante.Ecomerce_backend.rest;
//expone los endpoints para gestionar los usuarios y asignarles roles
import com.restaurante.Ecomerce_backend.service.UsuarioService;
import com.restaurante.Ecomerce_backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping  ("/usuario")

public class UsuarioRest {

    //expone los endpoints REST para interactuar con las entidades
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(path = "/listar")
    public ResponseEntity <List<Usuario>>ListarUser(){
        return ResponseEntity.ok(usuarioService.findAll());

    }


    /* Asignar un rol a un usuario*/
    @PostMapping("/{usuarioId}/asignar-rol/{rolId}")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        try {
            Usuario usuarioConRol = usuarioService.asignarRol(usuarioId, rolId);
            return ResponseEntity.ok(usuarioConRol);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Obtener usuarios por rol
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@PathVariable Long rolId) {
        List<Usuario> usuarios = usuarioService.findByRol(rolId);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
