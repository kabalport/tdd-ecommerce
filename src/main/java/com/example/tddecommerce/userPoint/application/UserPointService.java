package com.example.tddecommerce.userPoint.application;

import com.example.tddecommerce.userPoint.business.UserPoint;
import com.example.tddecommerce.userPoint.infrastructure.UserPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserPointService {

    @Autowired
    private UserPointRepository userPointRepository;
    @Transactional
    public void use(String userId, BigDecimal point) {
        UserPoint user = userPointRepository.findByUserId(userId);
        user.setPoint(user.getPoint().subtract(point));
        userPointRepository.save(user);
    }

}
