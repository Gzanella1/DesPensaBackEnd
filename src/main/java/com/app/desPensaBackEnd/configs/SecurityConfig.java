package com.app.desPensaBackEnd.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF (para testes com Postman, por ex.)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite acesso a todas as rotas
                )
                .formLogin(login -> login.disable()) // Remove a tela de login
                .httpBasic(basic -> basic.disable()); // Remove autenticação básica

        return http.build();
    }*/
}
