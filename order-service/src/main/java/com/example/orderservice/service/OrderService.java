package com.example.orderservice.service;

import com.example.orderservice.client.PaymentClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UpdateOrderRequest;
import com.example.orderservice.dto.UserSummary;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.exception.InvalidOrderException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderPaymentService orderPaymentService;
    private final PaymentClient paymentClient;
    private final UserClient userClient;
    private final OrderRepository orderRepository;

    public OrderService(
            PaymentClient paymentClient,
            UserClient userClient,
            OrderRepository orderRepository,
            OrderPaymentService orderPaymentService) {

        this.paymentClient = paymentClient;
        this.userClient = userClient;
        this.orderRepository = orderRepository;
        this.orderPaymentService = orderPaymentService;
    }

    // =========================================================
    // CREATE ORDER
    // =========================================================

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        // 1. Verify user
        UserSummary user =
                userClient.getUser(request.getUserId());

        // 2. Create Order
        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setStatus("CREATED");

        // 3. Create Order Items
        for (OrderItemRequest itemRequest : request.getItems()) {

            OrderItem item = new OrderItem();

            item.setProductId(itemRequest.getProductId());
            item.setQuantity(itemRequest.getQuantity());

            order.addItem(item);
        }

        // 4. Save Order + OrderItems
        Order savedOrder =
                orderRepository.save(order);

        // 5. Force failure for transaction rollback testing
        if (request.isForceFailure()) {

            throw new RuntimeException(
                    "Forced failure to test transaction rollback"
            );
        }

        // 6. Process Payment
        // Retry + Circuit Breaker are handled
        // inside OrderPaymentService.
        orderPaymentService.processPayment(
                false,
                false
        );

        // 7. Return response
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getStatus()
        );
    }

    // =========================================================
    // GET ORDER BY ID
    // =========================================================

    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order " + orderId + " was not found"
                        )
                );
    }

    // =========================================================
    // GET ORDER RESPONSE
    // =========================================================

    public OrderResponse getOrderResponse(Long orderId) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order " + orderId +
                                        " was not found"
                                )
                        );

        return new OrderResponse(
                order.getId(),
                order.getStatus()
        );
    }

    // =========================================================
    // GET ORDERS BY USER ID
    // =========================================================

    public List<OrderResponse> getOrdersByUserId(Long userId) {

        List<Order> orders =
                orderRepository.findByUserId(userId);

        return orders.stream()
                .map(order ->
                        new OrderResponse(
                                order.getId(),
                                order.getStatus()
                        )
                )
                .toList();
    }

    // =========================================================
    // UPDATE ORDER
    // =========================================================

    public OrderResponse updateOrder(
            Long orderId,
            UpdateOrderRequest request) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order " + orderId +
                                        " was not found"
                                )
                        );

        String newStatus =
                request.getStatus().toUpperCase();

        // Validate allowed statuses
        if (!newStatus.equals("CREATED")
                && !newStatus.equals("CONFIRMED")
                && !newStatus.equals("CANCELLED")) {

            throw new InvalidOrderException(
                    "Invalid order status: " + newStatus
            );
        }

        // Prevent updating an already cancelled order
        if ("CANCELLED".equals(order.getStatus())) {

            throw new InvalidOrderException(
                    "Cancelled order cannot be updated"
            );
        }

        order.setStatus(newStatus);

        Order updatedOrder =
                orderRepository.save(order);

        return new OrderResponse(
                updatedOrder.getId(),
                updatedOrder.getStatus()
        );
    }

    // =========================================================
    // DELETE ORDER
    // =========================================================

    public void deleteOrder(Long orderId) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order " + orderId +
                                        " was not found"
                                )
                        );

        orderRepository.delete(order);
    }
}