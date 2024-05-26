package com.example.tddecommerce.domain.userpoint.infrastructure;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.infrastructure.UserRepository;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserPointRepositoryTest extends IntegrationTest {

    @Autowired
    private IUserPointRepository userPointRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 사용자 생성 및 저장
        testUser = new User("Test User", "test@example.com");
        userRepository.createUser(testUser);
    }

    @Test
    void testFindByUserUserId() {
        // Given
        UserPoint userPoint = new UserPoint(testUser, BigDecimal.valueOf(100));
        userPointRepository.charge(userPoint);

        // When
        Optional<UserPoint> foundUserPoint = userPointRepository.findByUserUserId(testUser.getUserId());

        // Then
        assertThat(foundUserPoint).isPresent();
        assertThat(foundUserPoint.get().getPointBalance()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(foundUserPoint.get().getUser().getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    void testCharge() {
        // Given
        UserPoint userPoint = new UserPoint(testUser, BigDecimal.ZERO);
        userPointRepository.charge(userPoint);

        // When
        userPoint.addPoints(BigDecimal.valueOf(50));
        UserPoint updatedUserPoint = userPointRepository.charge(userPoint);

        // Then
        assertThat(updatedUserPoint.getPointBalance()).isEqualByComparingTo(BigDecimal.valueOf(50));
    }
}
