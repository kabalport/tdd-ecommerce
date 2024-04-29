package com.example.tddecommerce.member.infrastructure;

import com.example.tddecommerce.member.business.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionJpaRepository extends JpaRepository<PointTransaction,Long> {
}
