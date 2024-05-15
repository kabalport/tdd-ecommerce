package com.example.tddecommerce.userpoint.business.component;

import com.example.tddecommerce.userpoint.IUserPointRepository;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserPointCharger {


    private final IUserPointRepository iUserPointRepository;

    public UserPointCharger(IUserPointRepository iUserPointRepository) {
        this.iUserPointRepository = iUserPointRepository;
    }

    @Transactional
    public UserPoint execute(long userId, BigDecimal userPointAmount) {
        UserPoint userPoint = iUserPointRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserPoint not found for user id: " + userId));

        userPoint.addPoints(userPointAmount);
        return iUserPointRepository.charge(userPoint);
    }




}
