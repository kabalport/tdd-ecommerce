package com.example.tddecommerce.userPoint;

import java.math.BigDecimal;

class UserPointUseRequest {
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
