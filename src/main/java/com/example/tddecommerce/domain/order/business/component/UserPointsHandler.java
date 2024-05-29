package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserPointsHandler {

    private final UserPointReader userPointReader;
    private final UserPointValidator userPointValidator;

    public void handleUserPoints(Long userId, BigDecimal pointsToUse) {
        UserPoint currentUserPoint = userPointReader.readByUserId(userId);
        userPointValidator.validatePurchase(currentUserPoint, pointsToUse);
        currentUserPoint.decreasePoint(pointsToUse);
    }

    public UserPoint getUserPoints(Long userId) {
        return null;
    }
}
