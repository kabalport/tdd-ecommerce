package com.example.tddecommerce.userpoint;

import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findByUser_UserId(Long userId);
}

