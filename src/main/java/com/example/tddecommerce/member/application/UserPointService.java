package com.example.tddecommerce.member.application;

import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.domain.PointTransaction;
import com.example.tddecommerce.member.business.exception.UserPointBadRequestException;
import com.example.tddecommerce.member.business.exception.UserPointError;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import com.example.tddecommerce.member.business.repository.PointTransactionRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void charge(String userId, BigDecimal chargePoint) {
        validator.validate(chargePoint);

        Member member = memberRepository.findByUserIdLocked(userId).orElseThrow(() -> new UserPointBadRequestException(UserPointError.USER_NOT_FOUND));
        try {
        member.chargeUserPoint(chargePoint);
        pointTransactionRepository.save(PointTransaction.createByCharge(member, chargePoint));
        } finally {
            lockHandler.releaseLock(member);
        }
    }


    @Transactional
    public void use(String userId, BigDecimal usePoint) {
        validator.validate(usePoint);
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPointBadRequestException(UserPointError.USER_NOT_FOUND));
        try {
            member.useUserPoint(usePoint);
            pointTransactionRepository.save(PointTransaction.createByPayment(member, usePoint));
        } finally {
            lockHandler.releaseLock(member);
        }
    }

    private class LockHandler {
        public Member acquireLock(String userId) {
            return memberRepository.findByUserIdLocked(userId)
                    .orElseThrow(() -> new UserPointBadRequestException(UserPointError.USER_NOT_FOUND));
        }

        public void releaseLock(Member member) {
            // 로직에 따라 필요한 경우 락 해제 로직을 구현합니다.
            // JPA EntityManager를 통해 락을 해제할 수 있습니다.
            // 현재 상황에서 JPA의 @Transactional 어노테이션이 트랜잭션 종료 시 락을 자동으로 해제합니다.
        }
    }

    private final LockHandler lockHandler = new LockHandler();
}
