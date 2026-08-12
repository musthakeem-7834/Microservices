package com.example.orderservice.dto;

public class OrderResponseDTO {

    private Long orderId;
    private String product;
    private Integer quantity;
    private UserSummary user;

    public OrderResponseDTO(Long orderId, String product, Integer quantity, UserSummary user) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public UserSummary getUser() {
        return user;
    }
}