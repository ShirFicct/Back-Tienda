
package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Data
@Table(name = "user_suscripcion") // Tabla intermedia
public class UserSuscripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // Llave foránea que referencia a Usuario
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suscripcion_id") // Llave foránea que referencia a Suscripcion
    private Suscripcion suscripcion;

    private Date fechaInicio;
    private Date fechaFin;

    private boolean estado; // Estado de la suscripción (activa o no)

    // Constructor vacío
    public UserSuscripcion() {}

    // Constructor con parámetros
    public UserSuscripcion(Usuario usuario, Suscripcion suscripcion, Date fechaInicio,Date fechaFin, boolean estado) {
        this.usuario = usuario;
        this.suscripcion = suscripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
}

