package com.example.tddecommerce.member.concurrency.racecondition;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertTrue;



class UserAccountConcurrencyTest {

    // 이너 클래스로 UserAccount 정의
    static class UserAccount {
        private String username;
        private int points;

        public UserAccount(String username, int points) {
            this.username = username;
            this.points = points;
        }

        // 동기화를 제거하여 race condition 발생 가능성 증가
        public boolean usePoints(int pointsToUse) {
            if (this.points >= pointsToUse) {
                // 의도적으로 지연을 추가하여 race condition 발생 확률을 높임
                try {
                    Thread.sleep(30); // 스레드를 잠시 지연
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                this.points -= pointsToUse;
                return true;
            }
            return false;
        }

        public int getPoints() {
            return points;
        }
    }
    @Test
    public void testConcurrentPointUsage() throws InterruptedException {
        UserAccount account = new UserAccount("user1", 100);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        var futures = IntStream.range(0, 10)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> account.usePoints(10), executor))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        assertTrue(account.getPoints() != 0, "0이 아니면 레이스컨디션이 발생했을것이다.");

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
