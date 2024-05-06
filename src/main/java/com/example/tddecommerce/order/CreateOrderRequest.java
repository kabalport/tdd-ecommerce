package com.example.tddecommerce.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {
    private String userId;
    private Long customerId;
    private List<OrderItemDto> orderItems;
}
