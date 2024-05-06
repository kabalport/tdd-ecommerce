package com.example.tddecommerce.order;

import java.math.BigDecimal;

public class Product {
    private Long productId;
    private String name;
    private BigDecimal price;

    private ProductStock stock;

    public ProductStock getStock() {
        return stock;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
