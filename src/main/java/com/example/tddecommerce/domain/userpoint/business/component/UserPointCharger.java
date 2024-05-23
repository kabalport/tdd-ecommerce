package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.IUserPointRepository;
import com.example.tddecommerce.domain.userpoint.business.domain.UserPoint;
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
                .orElseGet(()->UserPoint.empty(userId));


        userPoint.addPoints(userPointAmount);
        return iUserPointRepository.charge(userPoint);
    }




}
