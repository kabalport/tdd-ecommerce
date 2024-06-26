package com.example.tddecommerce.domain.userpoint.application;

import com.example.tddecommerce.domain.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.setting.IntegrationTest;
import com.example.tddecommerce.domain.user.business.component.CreateUser;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
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
        String name = "홍길동";
        String email = "gildong@gmail.com";
        User user = userCreator.execute(name,email);
        // When
        UserPoint result = userPointService.chargeUserPoint(user.getUserId(), BigDecimal.valueOf(1000L));

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.valueOf(1000L), result.getPointBalance());
    }

    @Test
    void 잔고조회() {
        // Given
        String name = "홍길동";
        String email = "gildong@gmail.com";
        User user = userCreator.execute(name,email);

        BigDecimal requestChargeUserPoint = BigDecimal.valueOf(0L);

        // When
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
