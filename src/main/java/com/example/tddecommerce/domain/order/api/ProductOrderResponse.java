package com.example.tddecommerce.domain.order.api;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ProductOrderResponse {
    private Long orderId;
    private String status;
    private List<OrderItem> items;
    private BigDecimal totalAmount;

    @Getter
    @Builder
    public static class OrderItem {
        private Long productId;
        private int quantity;
        private BigDecimal price;
    }
}
