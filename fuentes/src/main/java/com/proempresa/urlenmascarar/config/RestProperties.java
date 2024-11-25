package com.proempresa.urlenmascarar.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
@Setter
public class RestProperties {

    @Value("${proempresa.schema}")
    private String schemaName;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}