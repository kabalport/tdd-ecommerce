package com.example.tddecommerce.member.infrastructure;

import com.example.tddecommerce.member.business.domain.PointTransaction;
import com.example.tddecommerce.member.business.repository.PointTransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PointTransactionCoreRepository implements PointTransactionRepository {

    private final PointTransactionJpaRepository pointTransactionJpaRepository;

    public PointTransactionCoreRepository(PointTransactionJpaRepository pointTransactionJpaRepository) {
        this.pointTransactionJpaRepository = pointTransactionJpaRepository;
    }

    @Override
    public void save(PointTransaction pointTransaction) {
        pointTransactionJpaRepository.save(pointTransaction);
    }
}
