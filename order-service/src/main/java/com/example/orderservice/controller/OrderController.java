package com.example.orderservice.controller;

import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/orders/payment")
    public String processPayment(
            @RequestParam(defaultValue = "false") boolean slow,
            @RequestParam(defaultValue = "false") boolean fail) {

        return orderService.processOrder(slow, fail);
    }
}