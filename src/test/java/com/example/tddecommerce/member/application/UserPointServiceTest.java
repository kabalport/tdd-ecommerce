package com.example.tddecommerce.member.application;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.member.UserPointStep;
import com.example.tddecommerce.member.api.UserPointRequest;
import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        UserPointRequest request = UserPointStep.유저포인트충전요청();
        // when
        userPointService.charge(request.getUserId(), request.getAddPoint());
        // then
        Member chargedMember = memberRepository.findByUserId(request.getUserId()).get();
        Assertions.assertEquals(request.getAddPoint(), chargedMember.getUserPoint());
    }



}
