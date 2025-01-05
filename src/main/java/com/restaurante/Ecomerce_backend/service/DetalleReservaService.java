package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.DetallePedidoDTO;
import com.restaurante.Ecomerce_backend.dto.DetalleReservaDTO;
import com.restaurante.Ecomerce_backend.model.*;
import com.restaurante.Ecomerce_backend.repositorios.DetalleReserRepository;
import com.restaurante.Ecomerce_backend.repositorios.InventarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DetalleReservaService {
    @Autowired
    private DetalleReserRepository detalleReserRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Detalle_reseva> findAll() {
        return detalleReserRepository.findAll();
    }

    public Detalle_reseva findById(Long id) {
        return detalleReserRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "no encontrado"));
    }

    // Ejemplo dentro de DetalleReservaService:

    @Transactional
    public Detalle_reseva crear(DetalleReservaDTO dto) {
        // 1. Obtén el producto
        Producto prod = productoService.obtenerProductoPorId(dto.getIdProducto());
        if (prod == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado");
        }

        // 2. Obtén la reserva
        Reserva res = reservaService.obtenerReservaPorId(dto.getIdReserva());
        if (res == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva no encontrada");
        }

        // 3. Identifica el inventario correcto
        //   (Sucede que en tu InventarioRepository tienes un método 'findByProductoIdAndSucursalIdAndTallaIdAndColorId')
        Inventario inventario = inventarioRepository.findByProductoIdAndSucursalIdAndTallaIdAndColorId(
                dto.getIdProducto(),
                dto.getSucursalId(),
                dto.getTallaId(),
                dto.getColorId()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));

        // 4. Chequea stock
        if (inventario.getStock() < dto.getCantidad()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        // 5. Descuenta stock de inmediato
        inventario.setStock(inventario.getStock() - dto.getCantidad());
        inventarioRepository.save(inventario);

        // 6. Crea y guarda el detalle de la reserva
        Detalle_reseva detalle = new Detalle_reseva();
        detalle.setReserva(res);
        detalle.setProducto(prod);
        detalle.setEstado(true);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        float subtotal = dto.getPrecioUnitario() * dto.getCantidad();
        detalle.setSubtotal(subtotal);

        // 7. Guarda el detalle
        Detalle_reseva detalleGuardado = detalleReserRepository.save(detalle);

        // 8. Suma subtotal al total de la reserva
        float nuevoTotal = res.getTotal() + subtotal;
        res.setTotal(nuevoTotal);
        reservaService.guardarCambios(res);

        return detalleGuardado;
    }

    public Detalle_reseva actualizar(Long id, DetalleReservaDTO detallerESDTO) {
        Detalle_reseva detalle_res = findById(id);
        detalle_res.setCantidad(detallerESDTO.getCantidad());
        detalle_res.setSubtotal(detallerESDTO.getCantidad()*detallerESDTO.getPrecioUnitario());
        detalle_res.setPrecioUnitario(detallerESDTO.getPrecioUnitario());
        return detalleReserRepository.save(detalle_res);

    }



    public void eliminar(Long id) {
        Detalle_reseva detalle_res = findById(id);
        detalle_res.setEstado(false);
        detalleReserRepository.save(detalle_res);
    }
}
