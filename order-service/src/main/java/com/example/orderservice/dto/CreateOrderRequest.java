package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class CreateOrderRequest {

    @NotNull(message = "userId is required")
    @Positive(message = "userId must be greater than 0")
    private Long userId;

    @NotNull(message = "items are required")
    @Valid
    private List<OrderItemRequest> items;

    private boolean forceFailure;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public boolean isForceFailure() {
        return forceFailure;
    }

    public void setForceFailure(boolean forceFailure) {
        this.forceFailure = forceFailure;
    }
}