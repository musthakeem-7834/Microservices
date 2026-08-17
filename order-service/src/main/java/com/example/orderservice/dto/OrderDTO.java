package com.example.orderservice.dto;

public class OrderDTO {

    private Long id;
    private String product;
    private Integer quantity;
    private Long userId;

    public OrderDTO(Long id, String product, Integer quantity, Long userId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getUserId() {
        return userId;
    }
}