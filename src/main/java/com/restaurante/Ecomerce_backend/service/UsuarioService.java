package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.dto.UsuarioDTO;
import com.restaurante.Ecomerce_backend.model.Idioma;
import com.restaurante.Ecomerce_backend.model.Rol;
import com.restaurante.Ecomerce_backend.model.Suscripcion;
import com.restaurante.Ecomerce_backend.model.Usuario;
import com.restaurante.Ecomerce_backend.repositorios.RolRepository;
import com.restaurante.Ecomerce_backend.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurante.Ecomerce_backend.config.JwtService;
import com.restaurante.Ecomerce_backend.response.AuthResponse;
import com.restaurante.Ecomerce_backend.response.LoginRequest;
import com.restaurante.Ecomerce_backend.service.RolService;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private IdiomaService idiomaService;

    public List<UsuarioDTO> listUser() {
        List<Usuario> user = usuarioRepository.findAll();
        return user.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public List<Usuario> listUsuario() {
        return usuarioRepository.findAll();
    }

    public Long getUsuariorById(String name) {
        Usuario usuario = usuarioRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return usuario.getId();
    }

    public Usuario obtenerUserPorId(Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }else {
            throw new UsernameNotFoundException("El usuario no se encuentra");
        }
    }

    public AuthResponse createUser(UsuarioDTO userDto) {
        Optional<Rol> optionalUserRole = rolRepository.findByNombre("CAJERO");
        Rol userRole = optionalUserRole.orElseGet(() -> rolRepository.save(new Rol ("CAJERO")));
        Set<Rol> roles = Collections.singleton(userRole);
        Usuario usuario = new Usuario( );
        usuario.setNombre(userDto.getNombre());
        usuario.setEmail(userDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDto.getPassword())) ;
        usuario.setRoles(roles);
        usuario.setActivo(true);
        usuario.setCredencialesNoExpiradas(true);
        usuario.setCuentaNoBloqueada(true);
        usuario.setCuentaNoExpirada(true);
        usuarioRepository.save(usuario);
        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
    }

    public Usuario registrarClient(UsuarioDTO userDto) {
//		List<Rol> roles  = rolService.listarRoles();
        Rol roles = rolService.obtenerRol(userDto.getIdRol());
        Set<Rol> rolesSet = new HashSet<>();
        Suscripcion suscripcio= suscripcionService.obtSuscripcionById(userDto.getId_Suscripcion());
        Idioma idioma= idiomaService.obtenerIdiomaPorId(userDto.getId_Idioma());

        rolesSet.add(roles);
        Usuario usuario = new Usuario();
        usuario.setNombre(userDto.getNombre());
                usuario.setEmail(userDto.getEmail());
                usuario.setDireccion(userDto.getDireccion());
                usuario.setNit(userDto.getNit());
                usuario.setIdioma(idioma);
                usuario.setTelefono(userDto.getTelefono());
                usuario.setPassword(passwordEncoder.encode(userDto.getPassword())) ;
                usuario.setRoles(rolesSet);
                usuario.setSuscripcion(suscripcio);
                usuario.setActivo(true);
                usuario.setCredencialesNoExpiradas(true);
                usuario.setCuentaNoBloqueada(true);
                usuario.setCuentaNoExpirada(true);

        return usuarioRepository.save(usuario);
    }

    public AuthResponse createUserAdmin(UsuarioDTO userDto) {
        Optional<Rol> optionalUserRole = rolRepository.findByNombre("ADMIN");
        Rol userRole = optionalUserRole.orElseGet(() -> rolRepository.save(new Rol ("ADMIN")));
        Set<Rol> roles = Collections.singleton(userRole);
        Usuario usuario = new Usuario( );
        usuario.setNombre(userDto.getNombre());
        usuario.setEmail(userDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(userDto.getPassword())) ;
        usuario.setRoles(roles);
        usuario.setActivo(true);
        usuario.setCredencialesNoExpiradas(true);
        usuario.setCuentaNoBloqueada(true);
        usuario.setCuentaNoExpirada(true);
        usuarioRepository.save(usuario);
        return AuthResponse.builder().token(jwtService.getToken(usuario)).build();
    }

    public Usuario getUser(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return usuario;
    }


    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user = usuarioRepository.findByEmail(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        Usuario userDetails = this.getUser(user.getUsername());
        return AuthResponse.builder()
                .token(token)
                .email(userDetails.getEmail())
                .role(userDetails.getRoles().iterator().next())
                .nombre(userDetails.getNombre())
                .id(userDetails.getId())
                .build();
    }

    public AuthResponse loader(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario user = usuarioDetailsService.getUser(userDetails.getUsername());
        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRoles().iterator().next())
                .nombre(user.getNombre())
                .id(user.getId())
                .build();
    }

    public Usuario updateUser(Long id, UsuarioDTO userDto) {
        Usuario user = obtenerUserPorId(id);
        user.setNombre(userDto.getNombre());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setNit(userDto.getNit());
        user.setDireccion(userDto.getDireccion());
        user.setTelefono(userDto.getTelefono());

        return usuarioRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Usuario user = obtenerUserPorId(id);
        user.setActivo(false);
        user.setCredencialesNoExpiradas(false);
        user.setCuentaNoBloqueada(false);
        user.setCuentaNoBloqueada(false);
    }

    @Transactional
    public void activeUser(Long id) {
        Usuario user = obtenerUserPorId(id);
        user.setActivo(true);
        user.setCredencialesNoExpiradas(true);
        user.setCuentaNoBloqueada(true);
        user.setCuentaNoBloqueada(true);
    }

    public List<Usuario> buscarUsuarios(String searchTerm) {
        return usuarioRepository.findByNombreOrApellidoOrEmail(searchTerm);
    }
}
