package com.example.tddecommerce.order.business.domain;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStock {
    private Long productStockId;

    private Long productId;
    private Product product;
    private int quantity;

    private LocalDateTime lastUpdated;

    public void increaseStock(int amount) {
        this.quantity += amount;
        this.lastUpdated = LocalDateTime.now();
    }

    public void decreaseStock(int amount) {
        if (this.quantity < amount) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }
        this.quantity -= amount;
        this.lastUpdated = LocalDateTime.now();
    }
}
