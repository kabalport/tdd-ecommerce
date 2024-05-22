package com.example.tddecommerce.order.business.model;

public enum ProductOrderStatus {
    PENDING, // 대기 중
    PAID,//결제됨
    CONFIRMED, // 확인됨
    SHIPPED, // 배송됨
    DELIVERED, // 배달됨
    CANCELLED // 취소됨
}
