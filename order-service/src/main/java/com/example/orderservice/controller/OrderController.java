package com.example.orderservice.controller;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UpdateOrderRequest;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long orderId) {

        OrderResponse response =
                orderService.getOrderResponse(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(
            @RequestParam Long userId) {

        List<OrderResponse> orders =
                orderService.getOrdersByUserId(userId);

        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderRequest request) {

        OrderResponse response =
                orderService.updateOrder(orderId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId) {

        orderService.deleteOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}