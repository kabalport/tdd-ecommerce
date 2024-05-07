package com.example.tddecommerce.order.business.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String name;
    private BigDecimal price;
    private ProductStock stock;


    public void increaseStock(int amount) {
        this.stock.increaseStock(amount);
    }

    public void decreaseStock(int amount) {
        this.stock.decreaseStock(amount);
    }
}
