package com.example.ecommercecicd.member.application;

import com.example.ecommercecicd.IntegrationTest;
import com.example.ecommercecicd.member.business.domain.Member;
import com.example.ecommercecicd.member.business.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
class UserPointServiceTest extends IntegrationTest {
    @Autowired
    private UserPointService userPointService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원이 포인트충전요청을 하면 회원의포인트를 충전시킵니다.")
    void 유저포인트충전() {
        // given
        Member member = new Member("user1",BigDecimal.ZERO);
        memberRepository.save(member);

        BigDecimal chargeAmount = BigDecimal.valueOf(20000L);
        // when
        userPointService.charge(member.getUserId(), chargeAmount);
        // then
        Member chargedMember = memberRepository.findById(member.getMemberId()).get();
        Assertions.assertEquals(chargeAmount, chargedMember.getUserPoint());
    }
}
