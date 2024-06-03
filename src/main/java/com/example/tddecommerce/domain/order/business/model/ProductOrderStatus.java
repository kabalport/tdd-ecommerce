package com.example.tddecommerce.domain.order.business.model;

public enum ProductOrderStatus {
    PENDING, // 대기 중
    PAID,//결제됨
    CONFIRMED, // 확인됨
    SHIPPED, // 배송됨
    DELIVERED, // 배달됨
    COMPLETED,
    CANCELLED // 취소됨
}
