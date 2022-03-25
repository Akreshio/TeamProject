package ru.intervale.TeamProject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ApplicationConfig config;

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Dinamic change price Book API")
                        .description("This is TeamTask Intervale Company")
                        .contact(new Contact().email(config.getEmail()))
                        .license(new License().name(config.getName()))
                        .version(config.getVersion()));
    }
}
