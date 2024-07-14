package com.marcinsz1993.eventmanagementkotlin.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition
class OpenApiConfiguration {

    @Bean
    fun openApi():OpenAPI{
        return OpenAPI()
            .info(Info()
                .contact(Contact()
                    .name("Marcin")
                    .email("marcinsz1993@hotmail.com"))
                .description("OpenApi documentation for EventManagementKotlin")
                .title("OpenApi specification - MarcinSz")
                .version("1.0")
                .license(License()
                    .name("License name")
                    .url("https://example-url.com"))
                .termsOfService("Terms of service"))
            .servers(listOf(Server()
                .description("Local ENV")
                .url("http://localhost:8080")))
            .security(listOf(SecurityRequirement()
                .addList("bearerAuth")))
            .schemaRequirement("bearerAuth", SecurityScheme()
                .name("bearerAuth")
                .description("JWT auth description")
                .scheme("bearer")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("bearer")
                .`in`(SecurityScheme.In.HEADER))
    }
}