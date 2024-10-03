package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ProductoDTO;
import com.restaurante.Ecomerce_backend.model.Producto;
import com.restaurante.Ecomerce_backend.model.Subcategoria;
import com.restaurante.Ecomerce_backend.model.Temporada;
import com.restaurante.Ecomerce_backend.model.Usuario;
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
    private SubCategoriaRepository subCategoriaRepository;

    // Listar todos los productos
    public List<Producto> listProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // Crear un nuevo producto
    public Producto crearProducto(ProductoDTO productoDTO) {
        if(productoDTO==null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no puede ser nula");
        }
        Subcategoria subcategoria = subCategoriaService.obtenerSubCatId(productoDTO.getIdSubcategoria());
        if (subcategoria == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategoria no encontrado");
        }

       Temporada temporada = temporadaService.obtTemporadaId(productoDTO.getIdTemporada());
        if (temporada == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Temporada no encontrado");
        }
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(productoDTO.getNombre());
        nuevoProducto.setPrecio(productoDTO.getPrecio());
        nuevoProducto.setDescripcion(productoDTO.getDescripcion());
        nuevoProducto.setColor(productoDTO.getColor());
        nuevoProducto.setTalla(productoDTO.getTalla());
        nuevoProducto.setMarca(productoDTO.getMarca());
        nuevoProducto.setStock(productoDTO.getStock());
        nuevoProducto.setImagen(productoDTO.getImagen());
        nuevoProducto.setSubcategoria(subcategoria);
        nuevoProducto.setTemporada(temporada);
        return productoRepository.save(nuevoProducto);

    }

    // Actualizar un producto
    public Producto actualizarProducto(Long id, ProductoDTO productoDetalles) {
        Producto producto = obtenerProductoPorId(id);
        if(producto==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        Temporada temporada=temporadaRepository.findById(productoDetalles.getIdTemporada())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "La temporada no fue encontrado"));

Optional<Subcategoria> subcategoria= Optional.ofNullable(subCategoriaRepository.findById(productoDetalles.getIdSubcategoria())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La subcategoria no fue encontrado")));

producto.setSubcategoria(subcategoria.get());
producto.setTemporada(temporada);

        producto.setNombre(productoDetalles.getNombre());
        producto.setDescripcion(productoDetalles.getDescripcion());
        producto.setMarca(productoDetalles.getMarca());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setTalla(productoDetalles.getTalla());
        producto.setColor(productoDetalles.getColor());
        producto.setImagen(productoDetalles.getImagen());
        producto.setStock(productoDetalles.getStock());
        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }
}
