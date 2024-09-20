package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        Optional<Rol> rol = rolService.getRolById(id);
        return rol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Guardar un nuevo rol
    @PostMapping("/guardar")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolService.save(rol));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        Optional<Rol> existingRol = rolService.getRolById(id);
        if (existingRol.isPresent()) {
            rol.setId(id);  // Asegurarse de que el ID sea correcto
            return ResponseEntity.ok(rolService.save(rol));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}

