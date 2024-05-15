package com.example.tddecommerce.user.component;

import com.example.tddecommerce.user.business.component.ReadUser;
import com.example.tddecommerce.user.business.repository.IUserRepository;
import com.example.tddecommerce.user.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ReadUserTest {
    private IUserRepository iUserRepository;
    private ReadUser readUser;

    @BeforeEach
    void setUp() {
        iUserRepository = mock(IUserRepository.class);

        readUser = new ReadUser(iUserRepository);
    }

    @Test
    @DisplayName("유저 조회")
    void readUser() {
        // Given
        Long userId = 1L;
        User user = UserFixture.createUserRequest(userId);

        given(iUserRepository.findById(userId)).willReturn(user);

        // When
        User result = readUser.readUser(userId);

        // Then
        assertThat(result.getName()).isEqualTo("홍길동");

    }

    private static class UserFixture {
        public static User createUserRequest(Long userId) {
            return new User(userId,"홍길동","aaa.com", LocalDateTime.now(),LocalDateTime.now());
        }
    }
}
