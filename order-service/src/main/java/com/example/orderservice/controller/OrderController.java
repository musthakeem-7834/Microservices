package com.example.orderservice.controller;

import com.example.orderservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {

        // sample order
        OrderDTO order = new OrderDTO(id, 1L);

        try {
            String userServiceUrl =
                    "http://localhost:8081/api/users/" + order.getUserId();

            UserDTO user = restTemplate.getForObject(userServiceUrl, UserDTO.class);

            OrderResponseDTO response =
                    new OrderResponseDTO(order.getOrderId(), user);

            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException.NotFound ex) {
            return ResponseEntity.status(404)
                    .body("User not found in User Service");

        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body("Unable to connect to User Service");
        }
    }
}