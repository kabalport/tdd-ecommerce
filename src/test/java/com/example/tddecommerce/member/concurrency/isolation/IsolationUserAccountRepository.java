package com.example.tddecommerce.member.concurrency.isolation;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IsolationUserAccountRepository extends JpaRepository<IsolationUserAccount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from IsolationUserAccount u where u.id = :id")
    IsolationUserAccount findByIdLocked(Long id);
}
