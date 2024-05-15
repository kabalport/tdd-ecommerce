package com.example.tddecommerce.user.application;

import com.example.tddecommerce.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.user.business.component.CreateUser;
import com.example.tddecommerce.user.business.component.ReadUser;
import com.example.tddecommerce.user.business.component.UpdateUser;
import com.example.tddecommerce.user.business.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final ReadUser readUser;
    private final CreateUser userCreator;

    private final UpdateUser updateUser;


    public UserService(ReadUser readUser, CreateUser createUser, UpdateUser updateUser) {
        this.readUser = readUser;
        this.userCreator = createUser;
        this.updateUser = updateUser;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return readUser.readUser(userId);
    }

    @Transactional
    public User addUser(CreateUserRequest request) {
        return userCreator.execute(request);
    }

    @Transactional
    public User updateUserName(Long userId, String newName) {

        User updateUserName = updateUser.updateUserName(userId, newName);

        return updateUserName;

    }
}
