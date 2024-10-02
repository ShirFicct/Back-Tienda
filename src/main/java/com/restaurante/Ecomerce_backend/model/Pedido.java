package com.restaurante.Ecomerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Data
@Table(name = "pedidos")
public class Pedido implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long Nro_pedido;
private Date fecha;
private boolean estado;
private String monto_total;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name ="id_pago")
    private Met_Pago met_Pago; //foranea heredada
}
