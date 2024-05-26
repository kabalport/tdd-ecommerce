package com.example.tddecommerce.domain.userpoint.infrastructure;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.infrastructure.UserRepository;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointTransactionRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserPointTransactionRepositoryTest extends IntegrationTest {

    @Autowired
    private IUserPointTransactionRepository userPointTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    private User testUser;
    private UserPoint testUserPoint;

    @BeforeEach
    void setUp() {
        // 사용자 생성 및 저장
        testUser = new User("Test User", "test@example.com");
        userRepository.createUser(testUser);

        // 사용자 포인트 생성 및 저장
        testUserPoint = new UserPoint(testUser, BigDecimal.ZERO);
        userPointJpaRepository.save(testUserPoint);
    }

    @Test
    void testSaveUserPointTransaction() {
        // Given
        UserPointTransaction userPointTransaction = new UserPointTransaction(
                testUserPoint, BigDecimal.valueOf(50), "CHARGE", "User charged points", LocalDateTime.now());

        // When
        userPointTransactionRepository.save(userPointTransaction);

        // Then
        assertThat(userPointTransaction.getPointTransactionId()).isNotNull();
        assertThat(userPointTransaction.getUserPoint().getUser().getUserId()).isEqualTo(testUser.getUserId());
        assertThat(userPointTransaction.getChangeAmount()).isEqualByComparingTo(BigDecimal.valueOf(50));
        assertThat(userPointTransaction.getTransactionType()).isEqualTo("CHARGE");
        assertThat(userPointTransaction.getDescription()).isEqualTo("User charged points");
    }
}
