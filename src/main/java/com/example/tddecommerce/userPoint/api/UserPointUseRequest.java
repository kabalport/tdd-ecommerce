package com.example.tddecommerce.userPoint.api;

import java.math.BigDecimal;

public class UserPointUseRequest {
    private final String userId;
    private final BigDecimal userPoint;

    public BigDecimal getUserPoint() {
        return userPoint;
    }

    public String getUserId() {
        return userId;
    }

    public UserPointUseRequest(String userId, BigDecimal userPoint) {
        this.userId = userId;
        this.userPoint = userPoint;
    }
}