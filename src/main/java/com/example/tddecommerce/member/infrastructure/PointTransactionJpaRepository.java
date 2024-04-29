package com.example.ecommercecicd.member.infrastructure;

import com.example.ecommercecicd.member.business.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionJpaRepository extends JpaRepository<PointTransaction,Long> {
}
