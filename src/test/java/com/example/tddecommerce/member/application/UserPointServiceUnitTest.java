package com.example.tddecommerce.member.application;
import com.example.tddecommerce.member.business.exception.UserPointBadRequestException;
import com.example.tddecommerce.member.MemberFixture;
import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.exception.UserPointError;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import com.example.tddecommerce.member.api.UserPointRequest;

import com.example.tddecommerce.member.business.repository.PointTransactionRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

class UserPointServiceUnitTest {
    private UserPointService userPointService;
    private MemberRepository memberRepository;
    private PointTransactionRepository pointTransactionRepository;



    @BeforeEach
    void setUp() {
        // 객체초기화
        memberRepository = Mockito.mock(MemberRepository.class);
        pointTransactionRepository = Mockito.mock(PointTransactionRepository.class);
        userPointService = new UserPointService(memberRepository, pointTransactionRepository);
    }

    @Test
    @DisplayName("포인트사용-회원이 포인트사용요청을 하면 회원의포인트를 사용합니다.")
    void 포인트사용(){
        // given
        String userId = "choi";
        BigDecimal userPoint = BigDecimal.valueOf(100000);
        Member member = new Member(userId, userPoint);
        Mockito.when(memberRepository.findByUserId(userId)).thenReturn(Optional.of(member));
        // when
        userPointService.use(userId, userPoint);
        // then
        Assertions.assertEquals(member.getUserPoint(),BigDecimal.ZERO);
    }

    @Test
    @DisplayName("포인트사용실패-잔액이 부족한경우는 포인트사용을 할 수 없습니다.")
    void 포인트사용실패_잔액부족() {
        // given
        String userId = "choi";
        BigDecimal userPoint = BigDecimal.valueOf(100000);
        Member member = new Member(userId, userPoint);
        Mockito.when(memberRepository.findByUserId(userId)).thenReturn(Optional.of(member));
        // when
        UserPointBadRequestException e = Assertions.assertThrows(UserPointBadRequestException.class, () -> userPointService.use(userId,  BigDecimal.valueOf(10000000)));
        // then
        Assertions.assertEquals(UserPointError.INSUFFICIENT_USER_POINT,e.getError());
    }

    @Test
    @DisplayName("포인트충전-회원이 포인트충전요청을 하면 회원의포인트를 충전시킵니다.")
    void 포인트충전() {
        // given
        Member member = MemberFixture.initMember();
        UserPointRequest request = MemberFixture.chargeRequest();
        Mockito.when(memberRepository.findByUserId(member.getUserId())).thenReturn(Optional.of(member));
        // when
        userPointService.charge(request.getUserId(),request.getAddPoint());
        // then
        Assertions.assertEquals(BigDecimal.valueOf(20000L), member.getUserPoint());
    }


    @Test
    @DisplayName("포인트충전실패-회원이 아닌경우는 포인트를 충전할수 없습니다.")
    void 포인트충전실패_유효하지않는회원이충전한경우() {
        // given
        UserPointRequest request = new UserPointRequest("1213123L", BigDecimal.valueOf(10000L));
        Mockito.when(memberRepository.findByUserId(request.getUserId())).thenReturn(Optional.empty());
        // when & then
        Assertions.assertThrows(UserPointBadRequestException.class, () -> {
            userPointService.charge(request.getUserId(), request.getAddPoint());
        });
    }







}
