package com.example.orderservice.client;

import com.example.orderservice.dto.UserSummary;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {

    private static final Logger log =
            LoggerFactory.getLogger(UserClient.class);

    private final RestClient restClient;
    private final CircuitBreakerFactory<?, ?> cbFactory;

    public UserClient(
            RestClient.Builder builder,
            CircuitBreakerFactory<?, ?> cbFactory) {

        this.restClient = builder
                .baseUrl("http://USER-SERVICE")
                .build();

        this.cbFactory = cbFactory;
    }

    @Retry(name = "userService")
    public UserSummary getUser(Long id) {

        log.info("Calling USER-SERVICE for user {}", id);

        CircuitBreaker circuitBreaker =
                cbFactory.create("userService");

        return circuitBreaker.run(

                () -> {
                    log.info("Calling USER-SERVICE...");

                    UserSummary user = restClient.get()
                            .uri("/api/users/{id}", id)
                            .retrieve()
                            .body(UserSummary.class);

                    log.info("USER-SERVICE success for user {}", id);

                    return user;
                },

                throwable -> {
                    log.error(
                            "USER-SERVICE failed for user {}: {}",
                            id,
                            throwable.getMessage()
                    );

                    return fallbackUser(id);
                }
        );
    }

    private UserSummary fallbackUser(Long id) {

        log.warn("Fallback executed for user {}", id);

        return new UserSummary(
                id,
                "User temporarily unavailable"
        );
    }
}