package com.example.tddecommerce.order;

import java.util.List;

public class CreateOrderRequest {
    private Long customerId;
    private List<OrderItemDto> orderItems;

    public CreateOrderRequest(Long customerId, List<OrderItemDto> orderItems) {
        this.customerId = customerId;
        this.orderItems = orderItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
}
