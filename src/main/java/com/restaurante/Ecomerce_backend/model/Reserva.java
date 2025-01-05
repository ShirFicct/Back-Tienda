package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "reserva")
public class Reserva implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Date fecha_creacion;
    private Date fecha_expiracion;
    private String estado;
    private float abono; // El 50% que se paga al reservar

    private float total;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_metpago")
    private Met_Pago met_Pago;
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;



}
