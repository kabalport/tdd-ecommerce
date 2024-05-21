package com.example.tddecommerce.product.api;

import com.example.tddecommerce.product.domain.model.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private DiscountPolicy discountPolicy;
    private int initialStock;
}
