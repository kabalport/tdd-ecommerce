package com.example.tddecommerce.member.application;

import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.domain.PointTransaction;
import com.example.tddecommerce.member.business.exception.UserPointBadRequestException;
import com.example.tddecommerce.member.business.exception.UserPointError;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import com.example.tddecommerce.member.business.repository.PointTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;



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
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UserPointBadRequestException(UserPointError.USER_NOT_FOUND));
        member.chargeUserPoint(chargePoint);
        pointTransactionRepository.save(PointTransaction.createByCharge(member, chargePoint));
    }


    public void use(String userId, BigDecimal usePoint) {
        validator.validate(usePoint);
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UserPointBadRequestException(UserPointError.USER_NOT_FOUND));
        member.useUserPoint(usePoint);
        pointTransactionRepository.save(PointTransaction.createByPayment(member, usePoint));
    }
}
