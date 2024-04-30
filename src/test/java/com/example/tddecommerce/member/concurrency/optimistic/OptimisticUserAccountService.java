package com.example.tddecommerce.member.concurrency.optimistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticUserAccountService {

    @Autowired
    private OptimisticUserAccountRepository repository;

    @Transactional
    public void decrementPoints(Long userId, int points) throws Exception {
        OptimisticUserAccount account = repository.findById(userId)
                .orElseThrow(() -> new Exception("아이디없음 :" + userId));
        if (account.getPoints() >= points) {
            account.setPoints(account.getPoints() - points);
            repository.save(account);
        } else {
            throw new Exception("잔액부족");
        }
    }

    @Transactional
    public void incrementPoints(Long userId, int points) throws Exception {
        OptimisticUserAccount account = repository.findById(userId)
                .orElseThrow(() -> new Exception("아이디없음 :" + userId));
        account.setPoints(account.getPoints() + points);
        repository.save(account);
    }
}
