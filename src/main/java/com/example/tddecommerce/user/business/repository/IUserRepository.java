package com.example.tddecommerce.user.business.repository;

import com.example.tddecommerce.user.business.domain.User;



public interface IUserRepository {
    void createUser(User user);

    User findById(Long userId);

    void modifyUser(User user);

}

