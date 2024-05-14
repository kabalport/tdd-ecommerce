package com.example.tddecommerce.user.business;

import com.example.tddecommerce.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

class UserServiceTest {
    private IUserRepository userRepository;
    private CreateMember createMember;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        createMember = new CreateMember(userRepository);
    }

    @Test
    @DisplayName("멤버를 생성한다.")
    void createMember(){
        String username = "username";
        String password = "password";
        String email = "email";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        final CreateMember.Request createMemberRequest = new CreateMember.Request(username,password,email,createdAt,updatedAt);
        //given


        // when
        createMember.request(createMemberRequest);
        // then
        // 멤버가 생성된다.
        // 멤버가 생성되었을때 기대 되어지는 객체를 만들고 검증한다.
        CreatedMemberExpectation createdMemberExpectation = new CreatedMemberExpectation(username, password, email, createdAt, updatedAt);
        Assertions.assertEquals(createdMemberExpectation,createdMemberExpectation);
    }

    private interface IUserRepository {
        void createUser(User user);
    }

    private class CreatedMemberExpectation {
        private final String username;
        private final String email;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public CreatedMemberExpectation(String username, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.username = username;
            this.email = email;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    private class CreateMember {

        private UserRepository userRepository;

        public CreateMember(IUserRepository userRepository) {
        }

        public User request(Request createMemberRequest) {

            final String name = createMemberRequest.username;
            final String email = createMemberRequest.email;

            final LocalDateTime createAt = createMemberRequest.createdAt;
            final LocalDateTime updatedAt = createMemberRequest.updatedAt;




            final User user = new User(name,email);
            userRepository.createUser(user);

            return user;
        }

        public record Request(String username, String password, String email, LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
        }
    }

    private class CreateMemberRequest {
        String username;
        String password;
        String email;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;




        public CreateMemberRequest(String username, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt
        ) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public CreateMemberRequest() {

        }
    }

    private interface UserRepository {
        void createUser(User user);
    }
}