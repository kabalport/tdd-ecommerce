package com.example.ecommercecicd.member;

import com.example.ecommercecicd.member.business.domain.Member;
import com.example.ecommercecicd.member.api.UserPointRequest;

import java.math.BigDecimal;

public class MemberFixture {
    public static Member initMember() {
        return new Member(1L, "zakigaebal", BigDecimal.valueOf(10000));
    }

    public static UserPointRequest chargeRequest() {
        Member member = MemberFixture.initMember();
        final String userId = member.getUserId();
        final BigDecimal addPoint = BigDecimal.valueOf(10000);
        return new UserPointRequest(userId, addPoint);
    }
}
