package ru.intervale.TeamProject.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "teamproject")
public class ApplicationConfig {

    private String name;

    private String email;

    private String version;
}
