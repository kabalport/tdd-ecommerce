package com.example.tddecommerce.domain.product.application;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;

import java.math.BigDecimal;

public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private DiscountPolicy discountPolicy;

    public CreateProductRequest(String name, BigDecimal price, String description, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }
}
