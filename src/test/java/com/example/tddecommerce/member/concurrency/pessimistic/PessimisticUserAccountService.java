package com.example.tddecommerce.member.concurrency.pessimistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticUserAccountService {

    @Autowired
    private PessimisticUserAccountRepository repository;

    @Transactional
    public void decrementPoints(Long userId, int points) {
        PessimisticUserAccount user = repository.findByIdLocked(userId);
        user.setPoints(user.getPoints() - points);
        repository.save(user);
    }
}
