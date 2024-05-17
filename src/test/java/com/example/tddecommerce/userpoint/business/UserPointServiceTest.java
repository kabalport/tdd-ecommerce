package com.example.tddecommerce.userpoint.business;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.user.business.component.CreateUser;
import com.example.tddecommerce.user.business.domain.User;
import com.example.tddecommerce.userpoint.application.UserPointService;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

class UserPointServiceTest extends IntegrationTest {

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private CreateUser userCreator;

    @Test
    void 포인트충전() {
        // Given
        CreateUserRequest createUserRequest = UserPointFixture.createUserRequest();
        User user = userCreator.execute(createUserRequest);

        // When
        UserPoint result = userPointService.chargeUserPoint(user.getUserId(), BigDecimal.valueOf(1000L));

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.valueOf(1000L), result.getPointBalance());
    }

    @Test
    void 잔고조회() {
        // Given
        CreateUserRequest createUserRequest = UserPointFixture.createUserRequest();
        User user = userCreator.execute(createUserRequest);
        BigDecimal requestChargeUserPoint = BigDecimal.valueOf(1000L);

        // When
        userPointService.chargeUserPoint(user.getUserId(), requestChargeUserPoint);
        UserPoint userPoint = userPointService.getUserPoint(user.getUserId());

        // Then
        Assertions.assertEquals(requestChargeUserPoint, userPoint.getPointBalance());
    }

    private static class UserPointFixture {
        public static CreateUserRequest createUserRequest() {
            return new CreateUserRequest("홍길동", "gildong@gmail.com");
        }
    }
}
