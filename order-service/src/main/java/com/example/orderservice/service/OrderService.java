package com.example.orderservice.service;

import com.example.orderservice.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final PaymentClient paymentClient;
    
    

    public OrderService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Retry(name = "paymentService")
    @CircuitBreaker(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )
    public String processOrder(boolean slow, boolean fail) {

        return paymentClient.callPaymentService(slow, fail);
    }

    public String paymentFallback(
            boolean slow,
            boolean fail,
            Throwable throwable) {

        return "Payment service is temporarily unavailable. Please try again later.";
    }
}
