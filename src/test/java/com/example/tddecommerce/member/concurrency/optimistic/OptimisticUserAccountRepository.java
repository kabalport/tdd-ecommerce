package com.example.tddecommerce.member.concurrency.optimistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimisticUserAccountRepository extends JpaRepository<OptimisticUserAccount, Long> {
}
