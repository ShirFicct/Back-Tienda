package com.restaurante.Ecomerce_backend.repositorios;
//operaciones de asignación de roles y búsqueda por rol.

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.model.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository < Usuario,Long>
{  // Puedes agregar métodos de consulta personalizados si es necesario
    List<Usuario> findByRol(Rol rol);
}
