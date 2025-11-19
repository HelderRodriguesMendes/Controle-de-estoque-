package com.desafiotecnico.stock_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Desafio Técnico - Microserviço")
                .description("API gerada para avaliação do processo seletivo")
                .version("1.0.0")
                .license(new License().name("Apache 2.0")))
            .externalDocs(new ExternalDocumentation()
                .description("Documentação Completa")
                .url("https://springdoc.org"));
    }
}
