package com.example.tddecommerce.userPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserPointManagerService {

    @Autowired
    private UserPointManagerRepository userPointManagerRepository;
    @Transactional
    public void use(String userId, BigDecimal point) {
        UserPoint user = userPointManagerRepository.findByUserId(userId);
        user.setPoint(user.getPoint().subtract(point));
        userPointManagerRepository.save(user);
    }


}
