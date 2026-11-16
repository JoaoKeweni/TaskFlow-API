package com.taskflow.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${CORS_ALLOWED_ORIGINS:http://localhost:5173,http://localhost:3000,http://localhost:4200}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica para todas as rotas (ex: /api/users, /api/boards)
                .allowedOrigins(allowedOrigins.split(",")) // Configure CORS_ALLOWED_ORIGINS com os domínios do frontend em produção
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") // Verbos HTTP liberados
                .allowedHeaders("*") // Aceita qualquer cabeçalho na requisição
                .allowCredentials(true); // Necessário se formos usar cookies para autenticação futuramente
    }
}
