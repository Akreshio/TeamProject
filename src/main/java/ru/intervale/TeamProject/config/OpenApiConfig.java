package ru.intervale.TeamProject.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Dinamic change price Book API")
                        .description("This is TeamTask Intervale Company")
                        .contact(new Contact().email("group-B@teamproject.ru"))
                        .license(new License().name("group-B: " +
                                                    "Дробышевский В., " +
                                                    "Прохорченко И., " +
                                                    "Маевский С., " +
                                                    "Самусев Д"))
                        .version("1.0"));
    }
}
