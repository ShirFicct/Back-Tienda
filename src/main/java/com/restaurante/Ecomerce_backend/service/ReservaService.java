package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.ReservaDTO;
import com.restaurante.Ecomerce_backend.model.Reserva;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.repositorios.ReservaRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todas las reservas
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    // Obtener una reserva por ID
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }

    // Crear una nueva reserva
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva no puede ser nula");
        }

        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        Reserva reserva = new Reserva();
        reserva.setFecha(reservaDTO.getFecha());
        reserva.setEstado(reservaDTO.isEstado());
        reserva.setUsuario(usuario);

        return reservaRepository.save(reserva);
    }

    // Modificar una reserva
    public Reserva modificarReserva(Long id, ReservaDTO reservaDTO) {
        Reserva reservaExistente = obtenerReservaPorId(id);
        if (reservaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        }

        Usuario usuario = usuarioRepository.findById(reservaDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        reservaExistente.setFecha(reservaDTO.getFecha());
        reservaExistente.setEstado(reservaDTO.isEstado());
        reservaExistente.setUsuario(usuario);

        return reservaRepository.save(reservaExistente);
    }

    // Eliminar una reserva
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);
        reservaRepository.delete(reserva);
    }
}
