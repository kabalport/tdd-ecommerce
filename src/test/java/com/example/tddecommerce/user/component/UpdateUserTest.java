package com.example.tddecommerce.user.component;

import com.example.tddecommerce.user.business.component.UpdateUser;
import com.example.tddecommerce.user.business.domain.User;
import com.example.tddecommerce.user.business.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateUserTest {
    private IUserRepository iUserRepository;
    private UpdateUser updateUser;

    @BeforeEach
    void setUp() {
        iUserRepository = mock(IUserRepository.class);
        updateUser = new UpdateUser(iUserRepository);
    }

    @Test
    @DisplayName("유저의 이름을 변경한다")
    void updateUserName() {
        // given
        final Long userId = 1L;
        final String nameUpdate = "철수";
        User user = UserFixture.createUserRequest(userId);

        given(iUserRepository.findById(userId)).willReturn(user);

        // when
        User result = updateUser.updateUserName(userId,nameUpdate);

        // then
        assertThat(result.getName()).isEqualTo(nameUpdate);
    }

    private static class UserFixture {
        public static User createUserRequest(Long userId) {
            return new User(userId,"홍길동","aaa.com", LocalDateTime.now(),LocalDateTime.now());
        }
    }
}
