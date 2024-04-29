package com.example.tddecommerce.member.concurrency.isolation;

import com.example.tddecommerce.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TransactionIsolationTest extends IntegrationTest {

    @Autowired
    private IsolationUserAccountRepository isolationUserAccountRepository;

    @Test
    @Transactional(isolation = Isolation.SERIALIZABLE)
    void testTransactionIsolation() throws InterruptedException {
        IsolationUserAccount user = new IsolationUserAccount("testuser", 100);
        isolationUserAccountRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable decrementTask = () -> {
            executeTransactionalOperation(user.getId(), 20);
        };

        service.execute(decrementTask);
        service.execute(decrementTask);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        IsolationUserAccount result = isolationUserAccountRepository.findByIdLocked(user.getId());
        assertEquals(60, result.getPoints());
    }

    @Transactional
    public void executeTransactionalOperation(Long userId, int decrement) {
        IsolationUserAccount account = isolationUserAccountRepository.findByIdLocked(userId);
        account.setPoints(account.getPoints() - decrement);
        isolationUserAccountRepository.save(account);
    }
}
