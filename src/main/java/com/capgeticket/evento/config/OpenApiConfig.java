package com.capgeticket.evento.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eventos API")
                        .description("Documentación Eventos API")
                        .version("v1.0")
                        .license(new License().name("LICENSE").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Descripción del proyecto")
                        .url("https://gsusag00.atlassian.net/wiki/spaces/CA/overview?homepageId=3604703"));
    }
}
