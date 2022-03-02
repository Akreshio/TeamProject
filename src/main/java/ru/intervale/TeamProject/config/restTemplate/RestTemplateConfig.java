
/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "02.03.2022, 18:46"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.config.restTemplate;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
public class RestTemplateConfig {

    /**
     * RestTemplate with default URI "https://ibapi.alfabank.by:8273"
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean("alfaBank")
    @Resource
    public RestTemplate restTemplateAlfaBank(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory("https://ibapi.alfabank.by:8273"))
                .additionalInterceptors(new CustomClientHttpRequestInterceptor())
                .build();
    }
}
