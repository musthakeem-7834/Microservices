package com.example.paymentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/api/payments")
    public String makePayment(
            @RequestParam(defaultValue = "false") boolean slow,
            @RequestParam(defaultValue = "false") boolean fail) {

        if (slow) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Payment interrupted";
            }
        }

        if (fail) {
            throw new RuntimeException("Payment service failed");
        }

        return "Payment successful";
    }
}
