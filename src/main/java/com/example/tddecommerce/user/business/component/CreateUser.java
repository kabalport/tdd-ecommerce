package com.example.tddecommerce.user.business.component;

import com.example.tddecommerce.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.user.business.repository.IUserRepository;
import com.example.tddecommerce.user.business.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateUser {

    private final IUserRepository iUserRepository;

    public CreateUser(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


    public User execute(CreateUserRequest createMemberRequest) {

        final String name = createMemberRequest.getName();
        final String email = createMemberRequest.getEmail();

        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();

        final User user = new User(name, email, createdAt, updatedAt);
        iUserRepository.createUser(user);

        return user;
    }

}
