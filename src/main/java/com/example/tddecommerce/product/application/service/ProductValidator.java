package com.example.tddecommerce.product.application.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    public void createRequestValidate(String name, BigDecimal price, int initialStock) {
        // 유효성 검사 로직 추가
        if (name == null || name.isEmpty()) {
            throw new ProductException("상품명은 필수입니다.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductException("Product price must be greater than zero");
        }
        if (initialStock < 0) {
            throw new ProductException("Initial stock cannot be negative");
        }
    }

    public void updateRequestValidate(String name, BigDecimal price) {
        // 유효성 검사 로직 추가
        if (name == null || name.isEmpty()) {
            throw new ProductException("상품명은 필수입니다.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductException("Product price must be greater than zero");
        }
    }
}
