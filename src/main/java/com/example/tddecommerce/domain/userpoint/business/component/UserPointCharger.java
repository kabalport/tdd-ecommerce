package com.example.tddecommerce.domain.userpoint.business.component;

import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointRepository;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
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
    public UserPoint execute(UserPoint userPoint) {

        return iUserPointRepository.charge(userPoint);
    }




}
