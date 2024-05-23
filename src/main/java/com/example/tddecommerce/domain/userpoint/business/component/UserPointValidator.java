package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.business.domain.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.exception.UserPointError;
import com.example.tddecommerce.domain.userpoint.business.exception.UserPointException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 포인트충전시 유효성검사를 수행합니다.
 */
@Component
public class UserPointValidator {
    public void validateChargeAmount(BigDecimal chargeAmount) {
        if(chargeAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new UserPointException(UserPointError.CHARGE_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void validatePurchase(UserPoint currentUserPoint, BigDecimal totalPurchaseAmount) {
        if (totalPurchaseAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new UserPointException(UserPointError.PURCHASE_AMOUNT_MUST_BE_POSITIVE);
        }
        if (currentUserPoint.getPointBalance().compareTo(totalPurchaseAmount) < 0) {
            throw new UserPointException(UserPointError.INSUFFICIENT_USER_POINT);
        }
    }
}
