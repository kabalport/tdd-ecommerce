package com.example.tddecommerce.domain.userpoint.api.dto;

import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;

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
