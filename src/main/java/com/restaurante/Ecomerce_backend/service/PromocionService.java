package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.PromocionDTO;
import com.restaurante.Ecomerce_backend.model.Promocion;
import com.restaurante.Ecomerce_backend.model.Suscripcion;
import com.restaurante.Ecomerce_backend.repositorios.PromocionRepository;
import com.restaurante.Ecomerce_backend.repositorios.SuscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PromocionService {

@Autowired
    private PromocionRepository promocionRepository;
    @Autowired
    private SuscripcionService suscripcionService;
    @Autowired
    private SuscripcionRepository suscripcionRepository;

    public List<Promocion> listPromo(){
    return promocionRepository.findAll();
}

public Promocion obtPromocionId (Long id){
    return promocionRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrado"));

}

public List<Promocion>getPromoBySuscr(Long id)
{
    Suscripcion suscripcion=suscripcionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));
return promocionRepository.findBySuscripcionId(suscripcion.getId());
}
 public Promocion crearPromocion (PromocionDTO promocion){
    Suscripcion suscripcion = suscripcionService.obtSuscripcionById(promocion.getSuscripcion());
    Promocion promo=new Promocion();
    promo.setNombre(promocion.getNombre());
    promo.setDescripcion(promocion.getDescripcion());
    promo.setDescuento(promocion.getDescuento());
    promo.setActivo(true);
    promo.setFecha_Inicio(promocion.getFecha_Inicio());
    promo.setFecha_Fin(promocion.getFecha_Fin());
    promo.setSuscripcion(suscripcion);
    return promocionRepository.save(promo);
 }

 public  Promocion actualizarPromocion (Long id, PromocionDTO promocion){
     Suscripcion suscripcion = suscripcionService.obtSuscripcionById(promocion.getSuscripcion());

     Promocion promoExist= obtPromocionId(id);
    promoExist.setNombre(promocion.getNombre());
    promoExist.setDescripcion(promocion.getDescripcion());
    promoExist.setDescuento(promocion.getDescuento());
    promoExist.setFecha_Inicio(promocion.getFecha_Inicio());
    promoExist.setFecha_Fin(promocion.getFecha_Fin());
    promoExist.setSuscripcion(suscripcion);

    return promocionRepository.save(promoExist);
 }

 public void eliminarPromocion (Long id){
    Promocion promocion= obtPromocionId(id);
    promocion.setActivo(false);
    promocionRepository.save(promocion);
 }


}
