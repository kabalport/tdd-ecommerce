package com.example.tddecommerce.domain.userpoint.infrastructure;

import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.userpoint.business.repository.IUserPointRepository;
import com.example.tddecommerce.domain.userpoint.infrastructure.UserPointJpaRepository;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPointRepository implements IUserPointRepository {

    private final UserPointJpaRepository userPointJpaRepository;

    @Override
    public Optional<UserPoint> findByUserUserId(long userId) {
        return userPointJpaRepository.findByUser_UserId(userId);
    }

    @Override
    public UserPoint charge(UserPoint userPoint) {
        return userPointJpaRepository.save(userPoint);
    }
}

