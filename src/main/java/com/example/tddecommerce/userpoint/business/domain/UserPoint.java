package com.example.tddecommerce.userpoint.business.domain;


import com.example.tddecommerce.user.business.domain.User;
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

    public static UserPoint empty() {
        UserPoint userPoint = new UserPoint();
        userPoint.pointBalance = BigDecimal.ZERO;
        return userPoint;
    }

    public static UserPoint empty(long userId) {
        UserPoint userPoint = new UserPoint();
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