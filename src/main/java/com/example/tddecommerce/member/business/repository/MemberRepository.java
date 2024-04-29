package com.example.ecommercecicd.member.business.repository;

import com.example.ecommercecicd.member.business.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(Long id);

    void save(Member member);

    Optional<Member> findByUserId(String userId);
}
