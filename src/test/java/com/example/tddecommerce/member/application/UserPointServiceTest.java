package com.example.tddecommerce.member.application;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.member.UserPointStep;
import com.example.tddecommerce.member.api.UserPointRequest;
import com.example.tddecommerce.member.business.domain.Member;
import com.example.tddecommerce.member.business.repository.MemberRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Transactional
class UserPointServiceTest extends IntegrationTest {
    @Autowired
    private UserPointService userPointService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원이 포인트충전요청을 하면 회원의포인트를 충전시킵니다.")
    void 유저포인트충전() {
        // given : 충전요청
        UserPointRequest request = UserPointStep.유저포인트충전요청();
        // when : 충전을 실행합니다.
        userPointService.charge(request.getUserId(), request.getAddPoint());
        // then : 요청한충전금액과 충전한유저의 유저포인트가 일치한지 확인합니다.
        Member chargedMember = memberRepository.findByUserId(request.getUserId()).get();
        Assertions.assertEquals(request.getAddPoint(), chargedMember.getUserPoint());
    }



    @Test
    @DisplayName("동시성 포인트 충전 테스트")
    void testConcurrentPointCharging() throws InterruptedException {
        int numberOfThreads = 10;
        BigDecimal chargeAmount = BigDecimal.valueOf(100);
        String userId = "testUser";
        Member member = new Member(userId, BigDecimal.ZERO);
        memberRepository.save(member);

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        Runnable chargeTask = () -> {
            try {
                startLatch.await();
                userPointService.charge(userId, chargeAmount);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                endLatch.countDown();
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(chargeTask);
        }

        startLatch.countDown(); // Start all threads
        endLatch.await(); // Wait for all threads to finish

        executor.shutdown();

        // Verify
        Member chargedMember = memberRepository.findByUserId(userId).orElseThrow();
        BigDecimal expectedTotal = chargeAmount.multiply(BigDecimal.valueOf(numberOfThreads));
        Assertions.assertEquals(expectedTotal, chargedMember.getUserPoint());
    }
}
