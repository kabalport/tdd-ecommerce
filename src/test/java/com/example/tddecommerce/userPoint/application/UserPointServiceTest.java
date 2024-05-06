package com.example.tddecommerce.userPoint.application;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.userPoint.api.UserPointResponse;
import com.example.tddecommerce.userPoint.api.UserPointUseRequest;
import com.example.tddecommerce.userPoint.business.UserPoint;
import com.example.tddecommerce.userPoint.infrastructure.IUserPointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPointServiceTest extends IntegrationTest {
    @Autowired
    private UserPointService userPointService;

    @Autowired
    private IUserPointRepository userPointRepository;

    @Test
    @DisplayName("동시성테스트-유저포인트")
    void 유저포인트사용() throws InterruptedException {
        String givenUserId = "testuser";
        BigDecimal givenUserPoint = BigDecimal.valueOf(100);
        UserPointUseRequest request = new UserPointUseRequest(givenUserId, givenUserPoint);

        UserPoint user = new UserPoint(request.getUserId(),request.getUserPoint());
        userPointRepository.save(user);

        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable task = () -> userPointService.use(user.getUserId(),BigDecimal.valueOf(50));


        service.execute(task);
        service.execute(task);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        UserPoint result = userPointRepository.findById(user.getId()).orElseThrow();
        UserPointResponse response = new UserPointResponse(result);
        assertEquals(0, response.getPoint());
    }
}
