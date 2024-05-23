package com.example.tddecommerce.domain.product.api;

import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductUpdateRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private DiscountPolicy discountPolicy;
}
