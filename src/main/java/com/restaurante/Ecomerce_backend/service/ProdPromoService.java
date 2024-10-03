
package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ProductoPromoDTO;
import com.restaurante.Ecomerce_backend.model.ProductoPromo;
import com.restaurante.Ecomerce_backend.model.Promocion;
import com.restaurante.Ecomerce_backend.repositorios.ProductoPromoRepository;
import com.restaurante.Ecomerce_backend.repositorios.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdPromoService  {

    @Autowired
    private ProductoPromoRepository productoPromoRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    // Listar todos los productos promocionales
    public List<ProductoPromo> listarProductosPromocionales() {
        return productoPromoRepository.findAll();
    }

    // Obtener un producto promocional por ID
    public ProductoPromo obtenerProductoPromoPorId(Long id) {
        return productoPromoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto promocional no encontrado"));
    }

    // Crear un nuevo producto promocional
    public ProductoPromo crearProductoPromo(ProductoPromoDTO productoPromoDTO) {
        if (productoPromoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto promocional no puede ser nulo");
        }

        Promocion promocion = promocionRepository.findById(productoPromoDTO.getIdPromo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Promoción no encontrada"));

        ProductoPromo productoPromo = new ProductoPromo();
        productoPromo.setId(productoPromoDTO.getCodigoP());
        productoPromo.setPromo(promocion);
        productoPromo.setEstado(productoPromoDTO.isEstado());

        return productoPromoRepository.save(productoPromo);
    }

    // Modificar un producto promocional
    public ProductoPromo modificarProductoPromo(Long id, ProductoPromoDTO productoPromoDTO) {
        ProductoPromo productoPromoExistente = obtenerProductoPromoPorId(id);
        if (productoPromoExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto promocional no encontrado");
        }

        Promocion promocion = promocionRepository.findById(productoPromoDTO.getIdPromo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promoción no encontrada"));

        productoPromoExistente.setId(productoPromoDTO.getCodigoP());
        productoPromoExistente.setPromo(promocion);
        productoPromoExistente.setEstado(productoPromoDTO.isEstado());

        return productoPromoRepository.save(productoPromoExistente);
    }

    // Eliminar un producto promocional
    public void eliminarProductoPromo(Long id) {
        ProductoPromo productoPromo = obtenerProductoPromoPorId(id);
        productoPromoRepository.delete(productoPromo);
    }
}
