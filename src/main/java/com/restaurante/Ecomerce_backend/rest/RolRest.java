package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolRest {

    @Autowired
    private RolService rolService;

    // Obtener todos los roles
    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.findAll());
    }

    // Guardar un nuevo rol
    @PostMapping("/guardar")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.save(rol));
    }
}

