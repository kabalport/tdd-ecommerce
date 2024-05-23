package com.example.tddecommerce.domain.user.business.component;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {


    private final IUserRepository iUserRepository;

    public UpdateUser(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }


    public User updateUserName(Long userId, String updateName) {
        User user = iUserRepository.findById(userId);
        user.updateName(updateName);

        iUserRepository.modifyUser(user);

        return user;
    }
}
