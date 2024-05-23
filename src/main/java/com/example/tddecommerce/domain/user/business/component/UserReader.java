package com.example.tddecommerce.domain.user.business.component;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
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

    public User readByUserId(Long userId) {
        return iUserRepository.findById(userId);
    }
}
