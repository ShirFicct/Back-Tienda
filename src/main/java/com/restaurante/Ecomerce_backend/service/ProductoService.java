package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ProductoDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.MarcaRepository;
import com.restaurante.Ecomerce_backend.repositorios.ProductoRepository;
import com.restaurante.Ecomerce_backend.repositorios.SubCategoriaRepository;
import com.restaurante.Ecomerce_backend.repositorios.TemporadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.tags.form.TagWriter;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TemporadaService temporadaService;
    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private SubCategoriaService subCategoriaService;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private SubCategoriaRepository subCategoriaRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private PromocionService promocionService;

    // Listar todos los productos
    public List<Producto> listProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public List<Producto> getProductosBySubcategoria(Long subcategoriaId) {
        // Validación: Verificar si la subcategoría existe
        Subcategoria subcategoria = subCategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Subcategoría no encontrada"));

        return productoRepository.findBySubcategoria_Id(subcategoria.getId());
    }

    public List<Producto> getProductosByTemporada(Long temporadaId) {
        // Validación: Verificar si la temporada existe
        Temporada temporada = temporadaRepository.findById(temporadaId)
                .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));

        return productoRepository.findByTemporada_Id(temporada.getId());
    }

    public List<Producto> getProductosByMarca(Long marcaId) {
        // Validación: Verificar si la temporada existe
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new IllegalArgumentException("Temporada no encontrada"));

        return productoRepository.findByMarca_Id(marca.getId());
    }


    // Crear un nuevo producto
    public Producto crearProducto(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no puede ser nula");
        }
        Subcategoria subcategoria = subCategoriaService.obtenerSubCatId(productoDTO.getIdSubcategoria());
        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategoria no encontrado");
        }
        Promocion promocion=promocionService.obtPromocionId(productoDTO.getIdPromocion());

        Temporada temporada = temporadaService.obtenerTemporadaPorId(productoDTO.getIdTemporada());
        if (temporada == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Temporada no encontrado");
        }
        Marca marca = marcaService.obtMarcaById(productoDTO.getId_marca());
        Producto nuevoProducto = new Producto();
        nuevoProducto.setCodProducto(productoDTO.getCodProducto());
        nuevoProducto.setNombre(productoDTO.getNombre());
        nuevoProducto.setPrecio(productoDTO.getPrecio());
        nuevoProducto.setDescripcion(productoDTO.getDescripcion());
        nuevoProducto.setMarca(marca);
        nuevoProducto.setImagenes(productoDTO.getImagenes());
        nuevoProducto.setSubcategoria(subcategoria);
        nuevoProducto.setTemporada(temporada);
        nuevoProducto.setPromocion(promocion);
        return productoRepository.save(nuevoProducto);

    }

    // Actualizar un producto
    public Producto actualizarProducto(Long id, ProductoDTO productoDetalles) {
        Producto producto = obtenerProductoPorId(id);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        Temporada temporada = temporadaRepository.findById(productoDetalles.getIdTemporada())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La temporada no fue encontrado"));
        Subcategoria subcategoria = subCategoriaService.obtenerSubCatId(productoDetalles.getIdSubcategoria());
        Marca marca = marcaService.obtMarcaById(productoDetalles.getId_marca());
        Promocion promocion=promocionService.obtPromocionId(productoDetalles.getIdPromocion());

        producto.setSubcategoria(subcategoria);
        producto.setTemporada(temporada);
        producto.setNombre(productoDetalles.getNombre());
        producto.setCodProducto(productoDetalles.getCodProducto());
        producto.setDescripcion(productoDetalles.getDescripcion());
        producto.setMarca(marca);
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setPromocion(promocion);
        producto.setImagenes(productoDetalles.getImagenes());
        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}
