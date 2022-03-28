/*
 * @author S.Maevsky
 * @since 24.03.2022, 11:43
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.external.belapb.config;

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
import ru.intervale.TeamProject.config.restTemplate.RequestLoggerInterceptor;

import javax.annotation.Resource;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
@Getter
@Setter
public class BelApbRestTemplateConfig {

    @Value("${rest.template.belagroprombank.url}")
    private String url;
    /**
     * RestTemplate with default URL "https://ibapi.alfabank.by:8273"
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean("belAgroPromBank")
    @Resource
    public RestTemplate restTemplateBelApb(RestTemplateBuilder builder) {

        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .additionalInterceptors(new RequestLoggerInterceptor())
                .build();
    }
}