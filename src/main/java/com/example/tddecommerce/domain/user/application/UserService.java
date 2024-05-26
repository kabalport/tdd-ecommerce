package com.example.tddecommerce.domain.user.application;

import com.example.tddecommerce.domain.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.domain.user.business.component.CreateUser;
import com.example.tddecommerce.domain.user.business.component.UserReader;
import com.example.tddecommerce.domain.user.business.component.UpdateUser;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserReader userReader;
    private final CreateUser userCreator;
    private final UpdateUser updateUser;

    public UserService(UserReader userReader, CreateUser createUser, UpdateUser updateUser) {
        this.userReader = userReader;
        this.userCreator = createUser;
        this.updateUser = updateUser;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userReader.readUser(userId);
    }

    @Transactional
    public User addUser(CreateUserRequest request) {
        return userCreator.execute(request);
    }

    @Transactional
    public User registerUser(String name, String email) {
        return userCreator.request(name, email);
    }

    @Transactional
    public User updateUserName(Long userId, String newName) {
        return updateUser.updateUserName(userId, newName);
    }
}
