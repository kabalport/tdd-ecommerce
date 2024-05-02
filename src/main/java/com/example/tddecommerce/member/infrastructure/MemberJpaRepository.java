package com.example.tddecommerce.member.infrastructure;

import com.example.tddecommerce.member.business.domain.Member;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {
    @Query("SELECT m FROM Member m WHERE m.memberId = ?1")
    Optional<Member> findById(Long id);
    @Query("SELECT m FROM Member m WHERE m.userId = ?1")
    Optional<Member> findByUserId(String userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Member m where m.userId = ?1")
    Optional<Member> findByUserIdLocked(String userId);
}
