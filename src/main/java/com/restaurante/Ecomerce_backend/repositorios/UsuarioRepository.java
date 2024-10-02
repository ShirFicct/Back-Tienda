package com.restaurante.Ecomerce_backend.repositorios;
//operaciones de asignación de roles y búsqueda por rol.

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository < Usuario,Long>
{  public Optional<Usuario> findByEmail(String email);

    @Query("SELECT c FROM Usuario c WHERE " +
            "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Usuario> findByNombreOrApellidoOrEmail(@Param("searchTerm") String searchTerm);



}
