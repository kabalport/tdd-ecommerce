package com.example.tddecommerce.domain.userpoint.business.model;


import com.example.tddecommerce.domain.user.business.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPointId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pointBalance = BigDecimal.ZERO;

    public UserPoint(User user, BigDecimal pointBalance) {
        this.user = user;
        this.pointBalance = pointBalance;
    }

    public UserPoint() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserPoint empty(Long userId) {
        UserPoint userPoint = new UserPoint();
        userPoint.user = new User(userId, null, null);  // Create a new User with userId
        userPoint.pointBalance = BigDecimal.ZERO;
        return userPoint;
    }

    public void addPoints(BigDecimal points) {
        this.pointBalance = this.pointBalance.add(points);
    }

    public void decreasePoint(BigDecimal points) {
        this.pointBalance = this.pointBalance.subtract(points);
    }
}