package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Promocion;
import com.restaurante.Ecomerce_backend.repositorios.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PromocionService {

@Autowired
    private PromocionRepository promocionRepository;

public List<Promocion> listPromo(){
    return promocionRepository.findAll();
}

public Promocion obtPromocionId (Long id){
    return promocionRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrado"));

}

 public Promocion crearPromocion (Promocion promocion){
    return promocionRepository.save(promocion);
 }

 public  Promocion actualizarPromocion (Long id, Promocion promocion){
    Promocion promoExist= obtPromocionId(promocion.getId());
    promoExist.setNombre(promocion.getNombre());
    promoExist.setDescripcion(promocion.getDescripcion());
    promoExist.setDescuento(promocion.getDescuento());
    return promocionRepository.save(promoExist);
 }

 public void eliminarPromocion (Long id){
    Promocion promocion= obtPromocionId(id);
    promocionRepository.delete(promocion);
 }


}
