package com.example.tddecommerce.userpoint.business.repository;

import com.example.tddecommerce.userpoint.business.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
}
