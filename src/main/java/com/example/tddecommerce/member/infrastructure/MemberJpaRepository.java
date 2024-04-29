package com.example.ecommercecicd.member.infrastructure;

import com.example.ecommercecicd.member.business.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {
    @Query("SELECT m FROM Member m WHERE m.memberId = ?1")
    Optional<Member> findById(Long id);
    @Query("SELECT m FROM Member m WHERE m.userId = ?1")
    Optional<Member> findByUserId(String userId);
}
