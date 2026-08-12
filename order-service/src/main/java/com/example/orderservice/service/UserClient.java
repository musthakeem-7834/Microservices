package com.example.orderservice.service;

import com.example.orderservice.dto.UserSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${user.service.base-url}")
    private String baseUrl;

    public UserSummary getUser(Long userId) {
        try {
            return restTemplate.getForObject(
                    baseUrl + "/api/users/" + userId,
                    UserSummary.class
            );
        } catch (RestClientException e) {
            return null;
        }
    }
}