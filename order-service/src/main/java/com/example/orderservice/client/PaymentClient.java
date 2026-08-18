package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentClient {

    private final RestClient restClient;

    public PaymentClient(
            @Qualifier("paymentRestClient") RestClient restClient) {

        this.restClient = restClient;
    }

    public String callPaymentService(
            boolean slow,
            boolean fail) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/payments")
                        .queryParam("slow", slow)
                        .queryParam("fail", fail)
                        .build())
                .retrieve()
                .body(String.class);
    }
}