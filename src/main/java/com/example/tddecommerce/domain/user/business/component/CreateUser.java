package com.example.tddecommerce.domain.user.business.component;

import com.example.tddecommerce.domain.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateUser {

    private final IUserRepository iUserRepository;

    public CreateUser(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


    public User execute(String name, String email) {

        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();

        final User user = new User(name, email, createdAt, updatedAt);
        iUserRepository.createUser(user);

        return user;
    }

    public User request(String name, String email) {

        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime updatedAt = LocalDateTime.now();

        final User user = new User(name, email, createdAt, updatedAt);
        iUserRepository.createUser(user);

        return user;
    }
}
