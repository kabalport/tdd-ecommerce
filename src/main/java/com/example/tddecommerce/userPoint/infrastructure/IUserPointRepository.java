package com.example.tddecommerce.userPoint.infrastructure;

import com.example.tddecommerce.userPoint.business.UserPoint;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserPointRepository extends JpaRepository<UserPoint,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserPoint u WHERE u.userId = ?1")
    UserPoint findByUserId(String userId);
}
