package com.capgeticket.evento.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Evento API")).

    }

}
