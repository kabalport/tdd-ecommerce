package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointTransactionRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserPointTransactionHistory {
    private final IUserPointTransactionRepository IUserPointTransactionRepository;

    public void add(UserPoint userPoint, BigDecimal chargeAmount, String transactionType, String description) {
        UserPointTransaction userPointTransaction = new UserPointTransaction();
        userPointTransaction.setUserPoint(userPoint);
        userPointTransaction.setChangeAmount(chargeAmount);
        userPointTransaction.setTransactionDate(LocalDateTime.now());
        userPointTransaction.setTransactionType(transactionType);
        userPointTransaction.setDescription(description);

        IUserPointTransactionRepository.save(userPointTransaction);
    }


}
