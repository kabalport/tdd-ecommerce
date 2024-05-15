package com.example.tddecommerce.userpoint.business.component;

import com.example.tddecommerce.userpoint.business.exception.UserPointError;
import com.example.tddecommerce.userpoint.business.exception.UserPointException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 포인트충전시 유효성검사를 수행합니다.
 */
@Component
public class UserPointValidator {
    public void validateChargeAmount(BigDecimal chargeAmount) {
        throw new UserPointException(UserPointError.CHARGE_AMOUNT_MUST_BE_POSITIVE);
    }
}
