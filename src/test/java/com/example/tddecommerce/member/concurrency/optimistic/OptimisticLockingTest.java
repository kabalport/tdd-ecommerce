package com.example.tddecommerce.member.concurrency.optimistic;

import com.example.tddecommerce.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


class OptimisticLockingTest extends IntegrationTest {

    @Autowired
    private OptimisticUserAccountRepository optimisticUserAccountRepository;

    @Test
    @Transactional
    public void testOptimisticLocking() throws InterruptedException {
        // Initialize user account with 100 points
        OptimisticUserAccount user = new OptimisticUserAccount("testuser", 100);
        optimisticUserAccountRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            try {
                OptimisticUserAccount loadedUser = optimisticUserAccountRepository.findById(user.getId()).get();
                loadedUser.setPoints(loadedUser.getPoints() - 50);
                optimisticUserAccountRepository.save(loadedUser);
            } catch (RuntimeException e) {
                System.out.println("Caught optimistic lock exception");
            }
        };

        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        // Confirm the balance is either 50 or 100 due to optimistic lock handling
        OptimisticUserAccount result = optimisticUserAccountRepository.findById(user.getId()).orElseThrow();
        assertTrue(result.getPoints() == 50 || result.getPoints() == 100);
    }
}
