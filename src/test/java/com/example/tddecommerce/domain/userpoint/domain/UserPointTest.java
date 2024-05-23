package com.example.tddecommerce.domain.userpoint.domain;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.domain.UserPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UserPointTest {
    @Test
    @DisplayName("신규 유저의 포인트 잔액은 0이어야 합니다.")
    void empty_신규유저테스트() {
        // when
        UserPoint userPoint = UserPoint.empty();

        // then
        Assertions.assertEquals(BigDecimal.ZERO, userPoint.getPointBalance(), "신규 유저의 포인트 잔액은 0이어야 합니다.");
    }

    @Test
    @DisplayName("포인트 충전 테스트")
    void addPoint_충전포인트테스트() {
        // given
        User user = new User(1L, "username", "email@example.com"); // 임의의 User 객체 생성
        UserPoint userPoint = new UserPoint(user, BigDecimal.ZERO);
        BigDecimal chargeAmount = new BigDecimal("100.00");

        // when
        userPoint.addPoints(chargeAmount);

        // then
        Assertions.assertEquals(chargeAmount, userPoint.getPointBalance(), "포인트 충전 후 잔액이 충전 금액과 일치해야 합니다.");
    }
}