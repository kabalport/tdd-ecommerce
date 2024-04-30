package com.example.tddecommerce.member.concurrency.isolation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class IsolationUserAccountService {
    @Autowired
    private IsolationUserAccountRepository repository;

    @Transactional
    public void decrementPoints(Long userId, int points) {
        IsolationUserAccount user = repository.findByIdLocked(userId);
        user.setPoints(user.getPoints() - points);
        repository.save(user);
    }
}
