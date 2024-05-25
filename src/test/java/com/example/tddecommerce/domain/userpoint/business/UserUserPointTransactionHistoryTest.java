package com.example.tddecommerce.domain.userpoint.business;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointTransactionHistory;
import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UserUserPointTransactionHistoryTest {

    private IUserPointTransactionRepository IUserPointTransactionRepository;
    private UserPointTransactionHistory userPointTransactionHistory;

    @BeforeEach
    void setUp() {
        IUserPointTransactionRepository = Mockito.mock(IUserPointTransactionRepository.class);
        userPointTransactionHistory = new UserPointTransactionHistory(IUserPointTransactionRepository);
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
        ArgumentCaptor<UserPointTransaction> captor = ArgumentCaptor.forClass(UserPointTransaction.class);
        verify(IUserPointTransactionRepository).save(captor.capture());
        UserPointTransaction capturedTransaction = captor.getValue();

        assertEquals(userPoint, capturedTransaction.getUserPoint());
        assertEquals(chargeAmount, capturedTransaction.getChangeAmount());
        assertEquals("CHARGE", capturedTransaction.getTransactionType());
        assertEquals("User charged points", capturedTransaction.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), capturedTransaction.getTransactionDate().getDayOfYear());
    }
}