package com.example.tddecommerce.userPoint;

import com.example.tddecommerce.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessimisticLockingTest extends IntegrationTest {

    @Autowired
    private PessimisticUserAccountService pessimisticUserAccountService;
    @Autowired
    private PessimisticUserAccountRepository repository;



    @Test
    void testPessimisticLocking() throws InterruptedException {
        PessimisticUserAccount user = new PessimisticUserAccount("testuser", 100);
        repository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> pessimisticUserAccountService.decrementPoints(user.getId(), 50);

        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        PessimisticUserAccount result = repository.findById(user.getId()).orElseThrow();
        assertEquals(0, result.getPoints());
    }



}
