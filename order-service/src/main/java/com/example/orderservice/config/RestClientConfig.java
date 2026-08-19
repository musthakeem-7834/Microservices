package com.example.orderservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    /*
     * Normal RestClient.Builder.
     *
     * @Primary tells Spring:
     * "When something asks for a RestClient.Builder
     * without a qualifier, use this one."
     *
     * Eureka will use this builder.
     */
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }


    /*
     * Load-balanced RestClient.Builder.
     *
     * This is ONLY used when we explicitly ask for
     * "loadBalancedRestClientBuilder".
     *
     * UserClient will use this builder to communicate
     * with USER-SERVICE through Eureka.
     */
    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedRestClientBuilder")
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }


    /*
     * Existing Payment Service RestClient.
     *
     * PaymentClient already uses:
     *
     * @Qualifier("paymentRestClient")
     */
    @Bean(name = "paymentRestClient")
    public RestClient paymentRestClient(
            @Value("${payment.service.base-url}") String paymentServiceBaseUrl) {

        return RestClient.builder()
                .baseUrl(paymentServiceBaseUrl)
                .build();
    }
}