package com.example.tddecommerce.domain.user.infrastructure;

import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.user.business.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void createUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void modifyUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾지 못했습니다. - id: " + userId));
    }

}
