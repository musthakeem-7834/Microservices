package com.example.orderservice.dto;

public class OrderResponseDTO {

    private Long orderId;
    private UserDTO user;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long orderId, UserDTO user) {
        this.orderId = orderId;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
