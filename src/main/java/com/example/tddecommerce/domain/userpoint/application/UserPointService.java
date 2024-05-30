package com.example.tddecommerce.domain.userpoint.application;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointTransactionHistory;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.domain.user.business.component.UserReader;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointCharger;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserPointService {
    private final UserPointValidator userPointValidator;
    private final UserReader userReader;
    private final UserPointReader userPointReader;
    private final UserPointCharger userPointCharger;
    private final UserPointTransactionHistory userPointTransactionHistory;
    /**
     *잔액 충전 기능
     * @param userId
     * @param chargeAmount
     * @return
     */
    public UserPoint chargeUserPoint(long userId, BigDecimal chargeAmount) {
        // 유효성 검증 - 충전금액을 확인합니다.
        userPointValidator.validateChargeAmount(chargeAmount);
        // 유저 조회
        User user = userReader.readUser(userId);
        // 잔액 조회 또는 신규 생성
        UserPoint userPoint = userPointReader.readByUserId(user.getUserId());
        // 충전 잔액 계산 : 기존 잔액에 충전금을 더합니다.
        userPoint.addPoints(chargeAmount);
        // 충전 처리 : 새로 계산된 충전금을 반영합니다.
        UserPoint chargedBalance = userPointCharger.execute(userPoint);
        // 잔액 충전 로그를 남깁니다.
        userPointTransactionHistory.add(chargedBalance, chargeAmount, "CHARGE", "User charged points");
        // 충전된 잔액 정보를 반환합니다.
        return chargedBalance;
    }


    /**
     * 잔액 조회기능
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserPoint getUserPoint(Long userId) {
        // 유저 검증
        User user = userReader.readUser(userId);
        // 유저의 기존포인트를 조회합니다.
        return userPointReader.readByUserId(user.getUserId());
    }

    public void addPoints(Long userId, BigDecimal chargeAmount) {
        // 유효성 검증 - 충전금액을 확인합니다.
        userPointValidator.validateChargeAmount(chargeAmount);
        // 유저 조회
        User user = userReader.readUser(userId);
        // 잔액 조회 또는 신규 생성
        UserPoint userPoint = userPointReader.readByUserId(user.getUserId());
        // 충전 잔액 계산 : 기존 잔액에 충전금을 더합니다.
        userPoint.addPoints(chargeAmount);
        // 충전 처리 : 새로 계산된 충전금을 반영합니다.
        UserPoint chargedBalance = userPointCharger.execute(userPoint);
        // 잔액 충전 로그를 남깁니다.
        userPointTransactionHistory.add(chargedBalance, chargeAmount, "CHARGE", "User charged points");
    }



    public UserPoint handleUserPoints(Long userId, BigDecimal pointsToUse) {
        UserPoint currentUserPoint = userPointReader.readByUserId(userId);
        userPointValidator.validatePurchase(currentUserPoint, pointsToUse);
        currentUserPoint.decreasePoint(pointsToUse);
        return currentUserPoint;
    }

    public UserPoint getUserPoints(Long userId) {
        return null;
    }
}
