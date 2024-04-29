package com.example.ecommercecicd.member.infrastructure;

import com.example.ecommercecicd.member.business.domain.Member;
import com.example.ecommercecicd.member.business.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberCoreRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    // 생성자를 public으로 변경
    public MemberCoreRepository(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return memberJpaRepository.findByUserId(userId);
    }
}
