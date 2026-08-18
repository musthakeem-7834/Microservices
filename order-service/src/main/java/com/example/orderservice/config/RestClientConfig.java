package com.example.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${user.service.base-url}")
    private String userServiceBaseUrl;

    @Value("${payment.service.base-url}")
    private String paymentServiceBaseUrl;

    @Bean
    public RestClient userRestClient() {

        return RestClient.builder()
                .baseUrl(userServiceBaseUrl)
                .build();
    }

    @Bean
    public RestClient paymentRestClient() {

        return RestClient.builder()
                .baseUrl(paymentServiceBaseUrl)
                .build();
    }
}