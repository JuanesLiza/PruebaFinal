package com.ejemplo.lavado_autos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration // Indica que esta clase define una configuración de Spring
@EnableWebFluxSecurity // Habilita la seguridad en aplicaciones WebFlux
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección contra CSRF
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Habilita autenticación básica HTTP
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN") // Asigna el rol de administrador
                .build();
        return new MapReactiveUserDetailsService(admin); // Retorna un servicio de usuarios en memoria
    }
    @Bean
    public PasswordEncoder passwordEncoder() { //Cifra contraseñas antes de guardarlas en la base de datos
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
