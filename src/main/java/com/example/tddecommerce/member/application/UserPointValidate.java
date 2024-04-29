package com.example.ecommercecicd.member.application;

import java.math.BigDecimal;

public class UserPointValidate {
    public void validate(BigDecimal chargePoint) throws IllegalArgumentException {
        if (chargePoint == null || chargePoint.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("충전 포인트는 0보다 커야 합니다.");
        }
    }
}
