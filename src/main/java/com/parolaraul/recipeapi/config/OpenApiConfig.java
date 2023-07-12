package com.parolaraul.recipeapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Api Key",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY).name("x-api-key").in(SecurityScheme.In.HEADER)))
                .info(
                        new Info()
                                .title("Recipe Service").version("0.0.1")
                                .description("Simple REST Api which allows users to manage their favourite recipes")
                                .contact(new Contact().name("Raul Parola").email("parolaraul@gmail.com"))
                );
    }
}
