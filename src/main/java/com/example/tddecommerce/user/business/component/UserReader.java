package com.example.tddecommerce.user.business.component;

import com.example.tddecommerce.user.business.repository.IUserRepository;
import com.example.tddecommerce.user.business.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final IUserRepository iUserRepository;

    public UserReader(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User readUser(Long userId) {
        return iUserRepository.findById(userId);
    }
}
