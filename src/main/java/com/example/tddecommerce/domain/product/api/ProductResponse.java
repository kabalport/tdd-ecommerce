package com.example.tddecommerce.domain.product.api;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private DiscountPolicy discountPolicy;
    private int initialStock;

    public ProductResponse(Long id, String name, BigDecimal price, String description, DiscountPolicy discountPolicy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPolicy = discountPolicy;
    }
}
