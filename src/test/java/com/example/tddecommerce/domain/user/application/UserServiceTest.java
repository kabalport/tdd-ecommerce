package com.example.tddecommerce.domain.user.application;

import com.example.tddecommerce.setting.IntegrationTest;
import com.example.tddecommerce.domain.user.api.CreateUserRequest;

import com.example.tddecommerce.domain.user.business.component.CreateUser;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


class UserServiceTest extends IntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CreateUser userCreator;

    @Test
    @DisplayName("유저를 생성한다.")
    void createUser(){
        // given
        final CreateUserRequest request = UserFixture.createUserRequest();

        // when
        User createdUser = userService.addUser(request);

        // then
        Assertions.assertEquals(request.getName(),createdUser.getName());
        Assertions.assertEquals(request.getEmail(),createdUser.getEmail());

        // Additional checks for createdAt and updatedAt
        Assertions.assertNotNull(createdUser.getCreatedAt(), "createdAt should not be null");
        Assertions.assertNotNull(createdUser.getUpdatedAt(), "updatedAt should not be null");
        Assertions.assertTrue(createdUser.getCreatedAt().isBefore(LocalDateTime.now()), "createdAt should be before now");
        Assertions.assertTrue(createdUser.getUpdatedAt().isBefore(LocalDateTime.now()), "updatedAt should be before now");
    }

    @Test
    @DisplayName("유저이름을 수정한다.")
    void updateUserName() {
        // given
        final CreateUserRequest request = UserFixture.createUserRequest();
        final User existUser = userCreator.execute(request);
        final Long userId = existUser.getUserId();
        final String userNameUpdate = "철수";
        // when
        User updateUser = userService.updateUserName(userId,userNameUpdate);

        // then
        Assertions.assertEquals(userNameUpdate,updateUser.getName());
    }

    @Test
    @DisplayName("유저조회한다.")
    void getUser() {
        // given
        final User existUser = userCreator.execute(UserFixture.createUserRequest());
        Long userId = existUser.getUserId();
        // when
        User updateUser = userService.getUserById(userId);

        // then
        Assertions.assertEquals(updateUser,updateUser);
    }

    private static class UserFixture {
        public static CreateUserRequest createUserRequest() {
            return new CreateUserRequest("홍길동", "gildong@gmail.com");
        }


    }
}