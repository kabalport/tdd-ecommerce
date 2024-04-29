package com.example.tddecommerce.member.concurrency.pessimistic;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessimisticUserAccountRepository extends JpaRepository<PessimisticUserAccount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from PessimisticUserAccount u where u.id = :id")
    PessimisticUserAccount findByIdLocked(Long id);
}
