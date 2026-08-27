package com.example.orderservice.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    /*
     * Normal RestClient.Builder.
     *
     * Used when Spring needs a normal RestClient.Builder.
     */
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }


    /*
     * Load-balanced RestClient.Builder.
     *
     * Used by UserClient to communicate with
     * USER-SERVICE through Eureka.
     *
     * DAY 9:
     * Added connection and response timeouts.
     */
    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedRestClientBuilder")
    public RestClient.Builder loadBalancedRestClientBuilder() {

        RequestConfig requestConfig = RequestConfig.custom()

                // Maximum time to establish connection
                .setConnectTimeout(
                        Timeout.ofSeconds(2)
                )

                // Maximum time waiting for a connection
                .setConnectionRequestTimeout(
                        Timeout.ofSeconds(2)
                )

                // Maximum time waiting for response
                .setResponseTimeout(
                        Timeout.ofSeconds(3)
                )

                .build();

        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setDefaultRequestConfig(requestConfig)
                        .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(
                        httpClient
                );

        return RestClient.builder()
                .requestFactory(requestFactory);
    }


    /*
     * Existing Payment Service RestClient.
     *
     * PaymentClient uses:
     *
     * @Qualifier("paymentRestClient")
     */
    @Bean(name = "paymentRestClient")
    public RestClient paymentRestClient(
            @Value("${payment.service.base-url}")
            String paymentServiceBaseUrl) {

        return RestClient.builder()
                .baseUrl(paymentServiceBaseUrl)
                .build();
    }
}