package com.example.tddecommerce.userPoint;

import java.math.BigDecimal;

// UserPointResponse 내부 클래스
class UserPointResponse {
    private BigDecimal point;

    public UserPointResponse(UserPoint userPoint) {
        this.point = userPoint.getPoint();
    }

    // BigDecimal을 int로 변환하여 반환
    public int getPoint() {
        return point.intValue();
    }
}
