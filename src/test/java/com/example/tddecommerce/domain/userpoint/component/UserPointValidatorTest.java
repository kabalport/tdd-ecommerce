package com.example.tddecommerce.domain.userpoint.component;

import com.example.tddecommerce.domain.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.domain.userpoint.business.exception.UserPointError;
import com.example.tddecommerce.domain.userpoint.business.exception.UserPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UserPointValidatorTest {
    private final UserPointValidator userPointValidator = new UserPointValidator();

    @Test
    @DisplayName("충전 금액이 양수일 때 유효성 검사를 통과합니다.")
    void 충전금액_양수_유효성검사() {
        // given
        BigDecimal chargeAmount = new BigDecimal("100.00");
        // when & then
        Assertions.assertDoesNotThrow(()->userPointValidator.validateChargeAmount(chargeAmount));
    }

    @Test
    @DisplayName("충전 금액이 0 또는 음수일 때 유효성 검사에서 예외를 던집니다.")
    void 충전금액_음수_유효성검사(){
        // given
        BigDecimal chargeAmountZero = BigDecimal.ZERO;
        BigDecimal chargeAmountNegative = new BigDecimal("-100.00");

        // when & then
        UserPointException exceptionZero = Assertions.assertThrows(UserPointException.class, () -> {
            userPointValidator.validateChargeAmount(chargeAmountZero);
        });
        Assertions.assertEquals(UserPointError.CHARGE_AMOUNT_MUST_BE_POSITIVE, exceptionZero.getError());

        UserPointException exceptionNegative = Assertions.assertThrows(UserPointException.class, () -> {
            userPointValidator.validateChargeAmount(chargeAmountNegative);
        });
        Assertions.assertEquals(UserPointError.CHARGE_AMOUNT_MUST_BE_POSITIVE, exceptionNegative.getError());
    }
}
