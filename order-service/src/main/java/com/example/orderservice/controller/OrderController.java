package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderResponseDTO;
import com.example.orderservice.dto.UserSummary;
import com.example.orderservice.service.UserClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final UserClient userClient;

    public OrderController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {

        // Temporary sample order
        OrderDTO order = new OrderDTO(id, "Laptop", 2, 1L);

        UserSummary user = userClient.getUser(order.getUserId());

        if (user == null) {
            return ResponseEntity.status(503)
                    .body("User Service is temporarily unavailable");
        }

        return ResponseEntity.ok(
                new OrderResponseDTO(
                        order.getId(),
                        order.getProduct(),
                        order.getQuantity(),
                        user
                )
        );
    }
}