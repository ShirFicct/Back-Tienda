package com.restaurante.Ecomerce_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SegurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	// Agregar el método para BCryptPasswordEncoder
	@Bean
	public BCryptPasswordEncoder customPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs REST; habilitarlo para formularios si es necesario
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
								"/tienda/v3/api-docs/**",  // Documentación OpenAPI
								"/tienda/swagger-ui/**",   // Recursos de Swagger UI
								"/tienda/swagger-ui.html", // Página principal de Swagger UI
								"/tienda/swagger-resources/**",
								"/tienda/webjars/**",
								"/tienda/plan",
								"/tienda/auth/**",
								"/v3/api-docs/**",
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/swagger-resources/**",
								"/webjars/**"
						).permitAll()             // Permitir acceso público a estas rutas
						.requestMatchers(
								"/tienda/registro**", "/tienda/js/**", "/tienda/css/**", "/tienda/img/**"
						).permitAll()
						.requestMatchers(HttpMethod.OPTIONS).permitAll()
						.anyRequest().authenticated() // Requerir autenticación para todo lo demás
				)
				.sessionManagement(sessionManager -> sessionManager
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para JWT
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}