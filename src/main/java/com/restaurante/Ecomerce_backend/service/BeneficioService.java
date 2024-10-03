package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.BeneficiosDTO;
import com.restaurante.Ecomerce_backend.dto.PedidoDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.BeneficioRepository;
import com.restaurante.Ecomerce_backend.repositorios.PromocionRepository;
import com.restaurante.Ecomerce_backend.repositorios.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BeneficioService {
    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;
    @Autowired
    private PromocionService promocionService;

    @Autowired
    private SuscripcionService suscripcionService;

    public Beneficio obtenerBeneficioPorId(Long id) {
        return beneficioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    public Beneficio crearBeneficio(BeneficiosDTO beneficioDTO) {
        if (beneficioDTO== null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beneficio no puede ser nula");
        }
        Promocion promo = promocionService.obtPromocionId(beneficioDTO.getIdPromo());
        if (promo== null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Promocion no encontrado");
        }
        Suscripcion suscripcion= suscripcionService.obtSuscripcionById(beneficioDTO.getIdSuscripcion());
        if (suscripcion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Suscripcion de pago no encontrado");
        }
Beneficio beneficio = new Beneficio();
        beneficio.setPromo(promo);
        beneficio.setSuscripcion(suscripcion);
        beneficio.setDescuento(beneficioDTO.getDescuento());
        return beneficioRepository.save(beneficio);
    }
    public Beneficio modificarBeneficio(Long id, BeneficiosDTO beneficioDTO) {
        Beneficio beneficioExistente = obtenerBeneficioPorId(id);
        if (beneficioExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficio no encontrado");
        }

        Promocion promo = promocionService.obtPromocionId(beneficioDTO.getIdPromo());
        if (promo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Promoción no encontrada");
        }

        Suscripcion suscripcion = suscripcionService.obtSuscripcionById(beneficioDTO.getIdSuscripcion());
        if (suscripcion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Suscripción no encontrada");
        }

        beneficioExistente.setPromo(promo);
        beneficioExistente.setSuscripcion(suscripcion);
        beneficioExistente.setDescuento(beneficioDTO.getDescuento());

        return beneficioRepository.save(beneficioExistente);
    }
    // Eliminar un beneficio
    public void eliminarBeneficio(Long id) {
        Beneficio beneficio = obtenerBeneficioPorId(id);
        beneficioRepository.delete(beneficio);
    }

}
