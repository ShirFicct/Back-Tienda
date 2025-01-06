package com.restaurante.Ecomerce_backend.rest;

import com.restaurante.Ecomerce_backend.model.Talla;
import com.restaurante.Ecomerce_backend.service.TallaService;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.Ecomerce_backend.util.HttpStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tallas")

public class TallaRest {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public ResponseEntity<List<Talla>> listarTallas() {
        List<Talla> tallas = tallaService.listarTallas();
        return ResponseEntity.ok(tallas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Talla> obtenerTallaPorId(@PathVariable Long id) {
        Talla talla = tallaService.obtenerTallaPorId(id);
        return ResponseEntity.ok(talla);
    }


    @PostMapping
    public ResponseEntity<Talla> crearTalla(@RequestBody Talla talla) {
        Talla nuevaTalla = tallaService.crearTalla(talla);
        return new ResponseEntity<>(nuevaTalla, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Talla> actualizarTalla(@PathVariable Long id, @RequestBody Talla talla) {
        Talla tallaExistente = tallaService.obtenerTallaPorId(id);

        tallaExistente.setNombre(talla.getNombre());
        Talla tallaActualizada = tallaService.actualizarTalla(tallaExistente);
        return ResponseEntity.ok(tallaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Talla> eliminarTalla(@PathVariable Long id) {
        Talla tallaCancelada = tallaService.eliminarTalla(id);
        return ResponseEntity.ok(tallaCancelada);
    }

}
