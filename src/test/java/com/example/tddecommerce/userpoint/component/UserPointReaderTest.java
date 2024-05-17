package com.example.tddecommerce.userpoint.component;

import com.example.tddecommerce.user.business.domain.User;
import com.example.tddecommerce.userpoint.IUserPointRepository;
import com.example.tddecommerce.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class UserPointReaderTest {


    private IUserPointRepository iUserPointRepository;

    private UserPointReader userPointReader;

    @BeforeEach
    void setUp() {
        iUserPointRepository = Mockito.mock(IUserPointRepository.class);
        userPointReader = new UserPointReader(iUserPointRepository);
    }

    @Test
    @DisplayName("유저의 기존포인트를 조회합니다.")
    void 유저포인트읽기() {
        // given
        User user = new User(1L, "username", "email@example.com");
        long userId = user.getUserId();
        UserPoint expectUserPoint = new UserPoint(user, BigDecimal.ZERO);
        given(iUserPointRepository.findByUserUserId(userId)).willReturn(Optional.of(expectUserPoint));

        // when
        UserPoint resultPoint = userPointReader.readByUserId(userId);

        // then
        // 신규유저는 포인트가 0원임을 반환합니다.
        Assertions.assertEquals(userId, resultPoint.getUser().getUserId());
    }
}
