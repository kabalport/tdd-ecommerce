package com.example.ecommercecicd.member.application;

import com.example.ecommercecicd.member.business.domain.Member;
import com.example.ecommercecicd.member.business.domain.PointTransaction;
import com.example.ecommercecicd.member.business.exception.UserPointBadRequestException;
import com.example.ecommercecicd.member.business.repository.MemberRepository;
import com.example.ecommercecicd.member.business.repository.PointTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.ecommercecicd.member.business.exception.UserPointError.USER_NOT_FOUND;

@Service
public class UserPointService {
    private final MemberRepository memberRepository;
    private final UserPointValidate validator = new UserPointValidate();
    private final PointTransactionRepository pointTransactionRepository;

    public UserPointService(MemberRepository memberRepository, PointTransactionRepository pointTransactionRepository) {
        this.memberRepository = memberRepository;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    public void charge(String userId, BigDecimal chargePoint) {
        validator.validate(chargePoint);
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UserPointBadRequestException(USER_NOT_FOUND));
        member.chargeUserPoint(chargePoint);
        pointTransactionRepository.save(PointTransaction.createByCharge(member, chargePoint));
    }


    public void use(String userId, BigDecimal usePoint) {
        validator.validate(usePoint);
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UserPointBadRequestException(USER_NOT_FOUND));
        member.useUserPoint(usePoint);
        pointTransactionRepository.save(PointTransaction.createByPayment(member, usePoint));
    }
}
