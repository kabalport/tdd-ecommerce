package com.example.tddecommerce.order;

import java.math.BigDecimal;

public class Product {
    private Long productId;
    private final String product1;
    private final BigDecimal bigDecimal;
    private String name;
    private BigDecimal price;

    private ProductStock stock;

    public Product(long productId, String product1, BigDecimal bigDecimal) {

        this.productId = productId;
        this.product1 = product1;
        this.bigDecimal = bigDecimal;
    }

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
