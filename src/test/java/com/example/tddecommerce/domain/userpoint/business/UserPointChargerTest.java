package com.example.tddecommerce.domain.userpoint.business;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointRepository;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointCharger;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;


class UserPointChargerTest {
    private UserPointCharger userPointCharger;
    private IUserPointRepository iUserPointRepository ;

    @BeforeEach
    void setUp() {
        iUserPointRepository = Mockito.mock(IUserPointRepository.class);
        userPointCharger = new UserPointCharger(iUserPointRepository);
    }

    @Test
    void 유저포인트충전() {
        // given
        User user = new User("홍길동", "gildong@gmail.com");
        Long userId = 1L;
        UserPoint userPoint = new UserPoint(user, BigDecimal.valueOf(500L));

        Mockito.when(iUserPointRepository.findByUserUserId(userId)).thenReturn(Optional.of(userPoint));
        Mockito.when(iUserPointRepository.charge(Mockito.any(UserPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        UserPoint updatedUserPoint = userPointCharger.execute(userPoint);

        // then
        Assertions.assertNotNull(updatedUserPoint);
        Assertions.assertEquals(userPoint.getPointBalance(), updatedUserPoint.getPointBalance());
    }

}
