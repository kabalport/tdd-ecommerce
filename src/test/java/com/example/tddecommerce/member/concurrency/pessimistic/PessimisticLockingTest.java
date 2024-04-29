package com.example.tddecommerce.member.concurrency.pessimistic;

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

import static org.junit.jupiter.api.Assertions.assertEquals;


class PessimisticLockingTest extends IntegrationTest {

    @Autowired
    private PessimisticUserAccountRepository pessimisticUserAccountRepository;

    @Test
    @Transactional
    void testPessimisticLocking() throws InterruptedException {
        PessimisticUserAccount user = new PessimisticUserAccount("testuser", 100);
        pessimisticUserAccountRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> {
            PessimisticUserAccount lockedUser = pessimisticUserAccountRepository.findByIdLocked(user.getId());
            lockedUser.setPoints(lockedUser.getPoints() - 50);
            pessimisticUserAccountRepository.save(lockedUser);
        };

        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        PessimisticUserAccount result = pessimisticUserAccountRepository.findById(user.getId()).orElseThrow();
        assertEquals(0, result.getPoints());
    }
}
