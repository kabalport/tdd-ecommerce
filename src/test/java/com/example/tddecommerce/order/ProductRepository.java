package com.example.tddecommerce.order;

import java.util.Optional;

public class ProductRepository {
    public Optional<Product> findById(Long productId) {
        // 상품 정보 조회 로직
        return Optional.of(new Product());
    }
}
