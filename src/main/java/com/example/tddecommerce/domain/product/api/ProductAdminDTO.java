package com.example.tddecommerce.domain.product.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


public class ProductAdminDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String userId; // 상품을 등록하는 사용자의 ID
        private String name; // 상품 이름
        private BigDecimal price; // 상품 가격
        private int initialStock; // 초기 재고 수량
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id; // 상품 ID
        private String name; // 상품 이름
        private BigDecimal price; // 상품 가격
        private int stock; // 현재 재고 수량
    }
}