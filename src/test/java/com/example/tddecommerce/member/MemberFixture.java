package com.example.tddecommerce.member;


import com.example.tddecommerce.member.api.UserPointRequest;
import com.example.tddecommerce.member.business.domain.Member;

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
