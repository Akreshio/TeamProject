
/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:46"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.config.restTemplate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import javax.annotation.Resource;

/**
 * The type Rest template config.
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
@Getter
@Setter
public class RestTemplateConfig {

    @Value("${rest.template.alfabank.url}")
    private String url;
    /**
     * RestTemplate with default URL "https://ibapi.alfabank.by:8273"
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean("alfaBank")
    @Resource
    public RestTemplate restTemplateAlfaBank(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .additionalInterceptors(new CustomClientHttpRequestInterceptor())
                .build();
    }
}
