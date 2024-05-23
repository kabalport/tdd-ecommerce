package com.example.tddecommerce.domain.userpoint.component;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointTransactionHistory;
import com.example.tddecommerce.domain.userpoint.business.domain.PointTransaction;
import com.example.tddecommerce.domain.userpoint.business.domain.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.PointTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UserPointTransactionHistoryTest {

    private PointTransactionRepository pointTransactionRepository;
    private UserPointTransactionHistory userPointTransactionHistory;

    @BeforeEach
    void setUp() {
        pointTransactionRepository = Mockito.mock(PointTransactionRepository.class);
        userPointTransactionHistory = new UserPointTransactionHistory(pointTransactionRepository);
    }

    @Test
    @DisplayName("포인트 충전 트랜잭션 로그 테스트")
    void 포인트_충전_트랜잭션_로그_테스트() {
        // given
        User user = new User(1L, "username", "email@example.com");
        UserPoint userPoint = new UserPoint(user, new BigDecimal("100.00"));
        BigDecimal chargeAmount = new BigDecimal("50.00");

        // when
        userPointTransactionHistory.add(userPoint, chargeAmount, "CHARGE", "User charged points");

        // then
        ArgumentCaptor<PointTransaction> captor = ArgumentCaptor.forClass(PointTransaction.class);
        verify(pointTransactionRepository).save(captor.capture());
        PointTransaction capturedTransaction = captor.getValue();

        assertEquals(userPoint, capturedTransaction.getUserPoint());
        assertEquals(chargeAmount, capturedTransaction.getChangeAmount());
        assertEquals("CHARGE", capturedTransaction.getTransactionType());
        assertEquals("User charged points", capturedTransaction.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), capturedTransaction.getTransactionDate().getDayOfYear());
    }
}