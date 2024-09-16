package com.restaurante.Ecomerce_backend.rest;
//expone los endpoints para gestionar los usuarios y asignarles roles
import com.restaurante.Ecomerce_backend.service.UsuarioService;
import com.restaurante.Ecomerce_backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping  ("/usuario")

public class UsuarioRest {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(path = "/listar")
    public ResponseEntity <List<Usuario>>ListarUser(){
        return ResponseEntity.ok(usuarioService.findAll());

    }

    // Guardar un nuevo usuario
    @PostMapping("/guardar")
    public ResponseEntity<Usuario> crear(@RequestBody Usuario u) {
       try {
           Usuario nuevo = usuarioService.save(u);
           return ResponseEntity.created(new URI("/usuario/guardar"  + nuevo.getId())).body(nuevo);
       }catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }

    }

    /* Asignar un rol a un usuario
    @PostMapping("/{usuarioId}/asignar-rol/{rolId}")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioService.asignarRol(usuarioId, rolId));
    }
    */

    // Obtener usuarios por rol
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioService.findByRol(rolId));
    }
}
