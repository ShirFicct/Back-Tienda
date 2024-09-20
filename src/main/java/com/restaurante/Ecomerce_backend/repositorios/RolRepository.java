package com.restaurante.Ecomerce_backend.repositorios;
//maneja las operaciones básicas de los roles.

import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.model.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface RolRepository extends JpaRepository <Rol,Long>
{
}