package com.example.orderservice.controller;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.UserSummary;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    public OrderController(
            OrderService orderService,
            UserClient userClient) {

        this.orderService = orderService;
        this.userClient = userClient;
    }

    @GetMapping("/api/orders/payment")
    public String processPayment(
            @RequestParam(defaultValue = "false") boolean slow,
            @RequestParam(defaultValue = "false") boolean fail) {

        return orderService.processOrder(slow, fail);
    }

    @GetMapping("/api/orders/{orderId}")
    public String getOrder(@PathVariable Long orderId) {

        UserSummary user = userClient.getUser(1L);

        return "Order " + orderId
                + " belongs to "
                + user.getName();
    }
}