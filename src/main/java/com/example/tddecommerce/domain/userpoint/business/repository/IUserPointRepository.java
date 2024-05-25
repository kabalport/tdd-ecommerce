package com.example.tddecommerce.domain.userpoint.business.repository;

import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;

import java.util.Optional;

public interface IUserPointRepository {
    Optional<UserPoint> findByUserUserId(long userId);

    UserPoint charge(UserPoint userPoint);
}
