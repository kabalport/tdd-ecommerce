package com.example.tddecommerce.userpoint.api;

import com.example.tddecommerce.userpoint.business.domain.UserPoint;

import java.math.BigDecimal;

// UserPointResponse 내부 클래스
public class UserPointResponse {
    private BigDecimal point;

    public UserPointResponse(UserPoint userPoint) {

    }

    // BigDecimal을 int로 변환하여 반환
    public int getPoint() {
        return point.intValue();
    }
}
