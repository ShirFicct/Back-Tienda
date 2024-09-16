package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.repositorios.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;
}
