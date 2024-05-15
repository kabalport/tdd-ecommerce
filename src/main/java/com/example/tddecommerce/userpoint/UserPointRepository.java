package com.example.tddecommerce.userpoint;

import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserPointRepository implements IUserPointRepository{
    @Override
    public Optional<UserPoint> findByUserUserId(long userId) {
        return Optional.empty();
    }

    @Override
    public UserPoint charge(UserPoint userPoint) {
        return null;
    }
}
