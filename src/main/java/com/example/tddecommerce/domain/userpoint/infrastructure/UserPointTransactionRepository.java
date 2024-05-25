package com.example.tddecommerce.domain.userpoint.infrastructure;

import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointTransactionRepository;
import com.example.tddecommerce.domain.userpoint.infrastructure.UserPointTransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPointTransactionRepository implements IUserPointTransactionRepository {

    private final UserPointTransactionJpaRepository userPointTransactionJpaRepository;

    @Override
    public void save(UserPointTransaction userPointTransaction) {
        userPointTransactionJpaRepository.save(userPointTransaction);
    }
}
