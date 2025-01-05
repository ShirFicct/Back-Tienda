package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Met_Pago;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.repositorios.Met_PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class Met_pagoService {
    @Autowired
    private Met_PagoRepository met_pagoRepository;

    public List<Met_Pago> ListMetodos() {
        return met_pagoRepository.findAll();
    }

    public Met_Pago obtMetoPorId(Long id) {
        return met_pagoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Metodo de pago no encontrado"));
    }

    public Met_Pago crearMetPago(Met_Pago met_pago) {
        Met_Pago met_pago_new = new Met_Pago();
        met_pago_new.setNombre(met_pago.getNombre());
        met_pago_new.setActivo(true);
        return met_pagoRepository.save(met_pago);
    }


    public Met_Pago actualizarMetPago(Long id, Met_Pago met_pago) {
        Met_Pago newMetpago = obtMetoPorId(id);
        newMetpago.setNombre(met_pago.getNombre());
        return met_pagoRepository.save(met_pago);

    }

    public Met_Pago eliminarMetPago(Long id) {
        Met_Pago newMetpago = obtMetoPorId(id);
        newMetpago.setActivo(false);
        return met_pagoRepository.save(newMetpago);
    }

}
