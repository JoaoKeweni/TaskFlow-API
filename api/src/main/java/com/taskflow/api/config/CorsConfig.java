package com.taskflow.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica para todas as rotas (ex: /api/users, /api/boards)
                .allowedOrigins("http://localhost:5173", "http://localhost:3000") // Libera o acesso para o React/Vite e Next.js
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") // Verbos HTTP liberados
                .allowedHeaders("*") // Aceita qualquer cabeçalho na requisição
                .allowCredentials(true); // Necessário se formos usar cookies para autenticação futuramente
    }
}
