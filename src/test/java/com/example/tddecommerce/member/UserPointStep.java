package com.example.tddecommerce.member;

import com.example.tddecommerce.member.api.UserPointRequest;
import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserPointStep {

    @Autowired
    private static MemberRepository memberRepository;


    public static UserPointRequest 유저포인트충전요청() {
        Member member = new Member("user1", BigDecimal.ZERO);
        memberRepository.save(member);
        BigDecimal chargeAmount = BigDecimal.valueOf(20000L);
        UserPointRequest userPointRequest = new UserPointRequest(member.getUserId(),chargeAmount);
        return userPointRequest;
    }
    
}
