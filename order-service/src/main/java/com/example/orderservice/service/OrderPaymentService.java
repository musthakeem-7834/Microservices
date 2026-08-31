package com.example.orderservice.service;

import com.example.orderservice.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentService {

    private final PaymentClient paymentClient;

    public OrderPaymentService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Retry(name = "paymentService")
    @CircuitBreaker(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )
    public String processPayment(
            boolean slow,
            boolean fail) {

        return paymentClient.callPaymentService(
                slow,
                fail
        );
    }

    public String paymentFallback(
            boolean slow,
            boolean fail,
            Throwable throwable) {

        throw new RuntimeException(
                "Payment Service failed. Order transaction will be rolled back.",
                throwable
        );
    }
}