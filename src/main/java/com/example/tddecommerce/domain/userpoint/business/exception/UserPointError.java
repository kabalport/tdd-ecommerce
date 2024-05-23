package com.example.tddecommerce.domain.userpoint.business.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserPointError {
    USER_NOT_FOUND("회원이 존재하지 않습니다."),
    CHARGE_AMOUNT_MUST_BE_POSITIVE("충전 금액은 0보다 커야 합니다."),
    BALANCE_CANNOT_BE_NEGATIVE("잔액은 음수가 될 수 없습니다."),
    INSUFFICIENT_USER_POINT("유저의 잔여 포인트가 부족합니다."),
    INTERNAL_SERVER_ERROR("서비스 내부 에러가 발생하였습니다."),
    LOCK_ACQUISITION_FAILED("작업을 시작하지 못했습니다."),
    PURCHASE_AMOUNT_MUST_BE_POSITIVE("구매 금액은 0보다 커야 합니다.");

    String errorMsg;

}


