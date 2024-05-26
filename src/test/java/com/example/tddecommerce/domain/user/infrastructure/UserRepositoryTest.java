package com.example.tddecommerce.domain.user.infrastructure;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.business.exception.UserException;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserRepositoryTest extends IntegrationTest {

    @Autowired
    private IUserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 사용자 생성 및 저장
        testUser = new User("Test User", "test@example.com");
        userRepository.createUser(testUser);
    }

    @Test
    void testCreateUser() {
        // Given
        User newUser = new User("New User", "new@example.com");

        // When
        userRepository.createUser(newUser);

        // Then
        User foundUser = userRepository.findById(newUser.getUserId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("New User");
        assertThat(foundUser.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void testModifyUser() {
        // Given
        testUser.updateName("Updated User");

        // When
        userRepository.modifyUser(testUser);

        // Then
        User foundUser = userRepository.findById(testUser.getUserId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Updated User");
    }

    @Test
    void testFindById() {
        // When
        User foundUser = userRepository.findById(testUser.getUserId());

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Test User");
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindById_UserNotFound() {
        // Given
        Long nonExistentUserId = 999L;

        // When & Then
        assertThrows(UserException.class, () -> userRepository.findById(nonExistentUserId));
    }
}
