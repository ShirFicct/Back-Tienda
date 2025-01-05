package com.restaurante.Ecomerce_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Getter
@Setter
@Table (name = "usuarios")
public class Usuario implements UserDetails, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    @Email(message = "El email debe ser válido")
    private String email;
    private String password;
    private String direccion;
    private String telefono;
    private String Nit;

    private boolean activo;
    private boolean cuentaNoExpirada;
    private boolean cuentaNoBloqueada;
    private boolean credencialesNoExpiradas;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_rol",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "idUsuario"),  // FK de Producto en la tabla intermedia
            inverseJoinColumns = @JoinColumn(name = "idRol")  // FK de Categoría en la tabla intermedia
    )
    @JsonIgnore
    private Set<Rol> roles;

    @ManyToOne
    @JoinColumn(name ="id_suscripcion")
    private Suscripcion suscripcion;

    @OneToOne
    @JoinColumn(name = "id_idioma") //foranea
    private Idioma idioma;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore  // Evita la serialización de los pedidos en la respuesta JSON
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore  // Evita la serialización de los pedidos en la respuesta JSON
    private List<Reserva> reserva;


    // Constructor vacío
    public Usuario() {}

  /* public Usuario(String nombre, String email, String password, Set<Rol> rol) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.roles = rol;
        this.activo = true;
        this.cuentaNoExpirada = true;
        this.cuentaNoBloqueada = true;
        this.credencialesNoExpiradas = true;
    }
*/
    //obtiene los nombres de los roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre()))
                .collect(Collectors.toSet());

        roles.forEach(role -> {
            System.out.println("Rol: " + role.getNombre());
            role.getPermiso().forEach(permiso -> {
                System.out.println("Permiso: " + permiso.getNombre());
                authorities.add(new SimpleGrantedAuthority(permiso.getNombre()));
            });
        });

        return authorities;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
