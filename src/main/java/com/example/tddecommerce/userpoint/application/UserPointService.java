package com.example.tddecommerce.userpoint.application;

import com.example.tddecommerce.user.business.component.ReadUser;
import com.example.tddecommerce.user.business.domain.User;
import com.example.tddecommerce.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserPointService {

    private final UserPointValidator userPointValidator;

    private final ReadUser readUser;
    private UserPointReader userPointReader;

    public UserPointService(UserPointValidator userPointValidator, ReadUser readUser) {
        this.userPointValidator = userPointValidator;
        this.readUser = readUser;
    }

    public UserPoint chargeUserPoint(long userId, BigDecimal chargeAmount) {
        // 유효성 검증 - 충전금액을 확인합니다.
        userPointValidator.validateChargeAmount(chargeAmount);
        // 유저 조회
        User user = readUser.readUser(userId);

        // 잔액 조회
        UserPoint userPoint = userPointReader.readByUserId(user.getUserId());


        // 충전 잔액 계산 : 기존 잔액에 충전금을 더합니다.

        // 충전 처리 : 새로 계산된 충전금을 반영합니다.

        // 잔액 충전 로그를 남깁니다.

        // 충전된 잔액 정보를 반환합니다.
        return null;
    }


    public UserPoint getUserPoint(Long userId) {

        return null;
    }

}
