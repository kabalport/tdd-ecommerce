package com.example.tddecommerce.order.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {
    private String userId;
    private List<OrderItemDto> orderItems;
}
