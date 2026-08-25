package com.example.orderservice.client;

import com.example.orderservice.dto.UserSummary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {

    private final RestClient restClient;
    private final CircuitBreakerFactory<?, ?> cbFactory;

    public UserClient(
            @Qualifier("loadBalancedRestClientBuilder")
            RestClient.Builder builder,
            CircuitBreakerFactory<?, ?> cbFactory) {

        this.restClient = builder
                .baseUrl("http://USER-SERVICE")
                .build();

        this.cbFactory = cbFactory;
    }

    public UserSummary getUser(Long id) {

        CircuitBreaker circuitBreaker =
                cbFactory.create("userService");

        return circuitBreaker.run(
                () -> restClient.get()
                        .uri("/api/users/{id}", id)
                        .retrieve()
                        .body(UserSummary.class),

                throwable -> fallbackUser(id)
        );
    }

    private UserSummary fallbackUser(Long id) {
        return new UserSummary(
                id,
                "User temporarily unavailable"
        );
    }
}