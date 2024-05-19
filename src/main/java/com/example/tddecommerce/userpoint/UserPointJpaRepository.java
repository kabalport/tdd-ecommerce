package com.example.tddecommerce.userpoint;

import com.example.tddecommerce.userpoint.business.domain.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long> {

    @Query("SELECT up FROM UserPoint up WHERE up.user.userId = :userId")
    Optional<UserPoint> findByUser_UserId(@Param("userId") Long userId);
}
