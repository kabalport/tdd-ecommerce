package com.example.tddecommerce.domain.user.business.repository;

import com.example.tddecommerce.domain.user.business.domain.User;


public interface IUserRepository {
    void createUser(User user);

    User findById(Long userId);

    void modifyUser(User user);

}

