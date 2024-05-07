package com.example.tddecommerce.order.api;

import java.math.BigDecimal;

public class OrderItemDto {
    private long productId;
    private int orderQuantity;
    private BigDecimal orderPrice;

    public long getProductId() {
        return productId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public OrderItemDto(long productId, int orderQuantity, BigDecimal orderPrice) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
    }
}
