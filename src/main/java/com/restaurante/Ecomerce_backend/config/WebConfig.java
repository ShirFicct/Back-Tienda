package com.restaurante.Ecomerce_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/tienda", c -> !c.getPackageName().startsWith("org.springdoc"));
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry){
        registry.addMapping("/**")
                //.allowedOrigins("http://localhost:5173") //aquí va la direccion de las peticiones
                .allowedOriginPatterns("**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
