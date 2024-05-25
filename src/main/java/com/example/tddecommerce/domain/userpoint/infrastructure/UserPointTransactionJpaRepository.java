package com.example.tddecommerce.domain.userpoint.infrastructure;

import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointTransactionJpaRepository extends JpaRepository<UserPointTransaction, Long> {
}
