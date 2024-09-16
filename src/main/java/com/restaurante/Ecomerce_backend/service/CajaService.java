package com.restaurante.Ecomerce_backend.service;


import com.restaurante.Ecomerce_backend.repositorios.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajaService  {
    @Autowired
    private CajaRepository cajaRepository;
}
