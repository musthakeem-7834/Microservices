package com.example.orderservice.client;

import com.example.orderservice.dto.UserSummary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {

    private final RestClient restClient;

    public UserClient(
            @Qualifier("userRestClient") RestClient restClient) {

        this.restClient = restClient;
    }

    public UserSummary getUser(Long userId) {

        return restClient.get()
                .uri("/api/users/{id}", userId)
                .retrieve()
                .body(UserSummary.class);
    }
}
