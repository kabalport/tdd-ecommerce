package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.business.domain.PointTransaction;
import com.example.tddecommerce.domain.userpoint.business.domain.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.PointTransactionRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserPointTransactionHistory {
    private final PointTransactionRepository pointTransactionRepository;

    public void add(UserPoint userPoint, BigDecimal chargeAmount, String transactionType, String description) {
        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.setUserPoint(userPoint);
        pointTransaction.setChangeAmount(chargeAmount);
        pointTransaction.setTransactionDate(LocalDateTime.now());
        pointTransaction.setTransactionType(transactionType);
        pointTransaction.setDescription(description);

        pointTransactionRepository.save(pointTransaction);
    }


}
