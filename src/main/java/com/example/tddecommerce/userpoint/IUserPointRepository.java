package com.example.tddecommerce.userpoint;

import com.example.tddecommerce.userpoint.business.domain.UserPoint;

import java.util.Optional;

public interface IUserPointRepository {
    Optional<UserPoint> findByUserUserId(long userId);

    UserPoint charge(UserPoint userPoint);
}
