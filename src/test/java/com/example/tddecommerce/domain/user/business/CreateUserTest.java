package com.example.tddecommerce.domain.user.business;


import com.example.tddecommerce.domain.user.api.CreateUserRequest;
import com.example.tddecommerce.domain.user.business.component.CreateUser;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;

class CreateUserTest {

    private CreateUser createUser;
    private IUserRepository iUserRepository;

    @BeforeEach
    void setUp() {
        iUserRepository = mock(IUserRepository.class);
        createUser = new CreateUser(iUserRepository);
    }

    @Test
    @DisplayName("유저 생성")
    void createUser() {
        // given
        CreateUserRequest createUserRequest = UserFixture.createUserRequest();

        // when
        User createdUser = createUser.execute(createUserRequest);

        // then
        Assertions.assertNotNull(createdUser);
    }



    private static class UserFixture {
        public static CreateUserRequest createUserRequest() {
            return new CreateUserRequest("홍길동", "gildong@gmail.com");
        }
    }
}
