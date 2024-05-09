package com.example.tddecommerce.user.business;

import com.example.tddecommerce.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class UserServiceTest {
    private IUserRepository userRepository;

    @Test
    @DisplayName("멤버를 생성한다.")
    void createMember(){

        // then
        // 멤버가 생성된다.
        // 멤버가 생성되었을때 기대 되어지는 객체를 만들고 검증한다.
        String username = "username";
        String password = "password";
        String email = "email";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        CreatedMemberExpectation createdMemberExpectation = new CreatedMemberExpectation(username, password, email, createdAt, updatedAt);
    }

    private interface IUserRepository {
    }

    private class CreatedMemberExpectation {
        private final String username;
        private final String password;
        private final String email;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public CreatedMemberExpectation(String username, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}