package com.example.tddecommerce.member.concurrency;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.member.concurrency.optimistic.OptimisticUserAccount;
import com.example.tddecommerce.member.concurrency.optimistic.OptimisticUserAccountRepository;
import com.example.tddecommerce.member.concurrency.optimistic.OptimisticUserAccountService;
import com.example.tddecommerce.member.concurrency.pessimistic.PessimisticUserAccount;
import com.example.tddecommerce.member.concurrency.pessimistic.PessimisticUserAccountRepository;
import com.example.tddecommerce.member.concurrency.pessimistic.PessimisticUserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConcurrencyLockingTests extends IntegrationTest {
    @Autowired
    private OptimisticUserAccountRepository optimisticUserAccountRepository;
    @Autowired
    private PessimisticUserAccountRepository pessimisticUserAccountRepository;

    @Autowired
    private OptimisticUserAccountService optimisticUserAccountService;
    @Autowired
    private PessimisticUserAccountService pessimisticUserAccountService;

    //    @Transactional(isolation = Isolation.SERIALIZABLE)


    @Test
    @Transactional
    void testOptimisticLocking() throws InterruptedException {
        OptimisticUserAccount user = new OptimisticUserAccount("testuser", 100);
        optimisticUserAccountRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            try {
                OptimisticUserAccount loadedUser = optimisticUserAccountRepository.findById(user.getId()).get();
                loadedUser.setPoints(loadedUser.getPoints() - 50);
                optimisticUserAccountRepository.save(loadedUser);
            } catch (RuntimeException e) {
            }
        };

        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        OptimisticUserAccount result = optimisticUserAccountRepository.findById(user.getId()).orElseThrow();
        assertTrue(result.getPoints() == 50 || result.getPoints() == 100, "포인트는 50이거나 100이다.");
    }

    @Test
    void testPessimisticLocking() throws InterruptedException {
        PessimisticUserAccount user = new PessimisticUserAccount("testuser", 100);
        pessimisticUserAccountRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> pessimisticUserAccountService.decrementPoints(user.getId(), 50);

        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        PessimisticUserAccount result = pessimisticUserAccountRepository.findById(user.getId()).orElseThrow();
        assertEquals(0, result.getPoints(), "포인트는 0이되어야한다");
    }
}
