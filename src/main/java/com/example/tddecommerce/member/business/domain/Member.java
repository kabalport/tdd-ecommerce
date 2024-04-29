package com.example.ecommercecicd.member.business.domain;

import com.example.ecommercecicd.member.business.exception.UserPointBadRequestException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.example.ecommercecicd.member.business.exception.UserPointError.INSUFFICIENT_USER_POINT;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    private String userId;
    private BigDecimal userPoint;

    public Member(long memberId, String userId, BigDecimal userPoint) {
        this.memberId = memberId;
        this.userId = userId;
        this.userPoint = userPoint;
    }

    public Member(String userId, BigDecimal userPoint) {

        this.userId = userId;
        this.userPoint = userPoint;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getUserPoint() {
        return userPoint;
    }

    public void chargeUserPoint(BigDecimal chargePoint) {
        if (chargePoint != null) {
            this.userPoint = this.userPoint.add(chargePoint);
        }
    }

    public void assignId(Long id) {
        this.memberId = id;
    }

    public void setUserPoint(BigDecimal userPoint) {
        this.userPoint = userPoint;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void useUserPoint(BigDecimal usePoint) {
        if (usePoint == null) {
            throw new IllegalArgumentException("The amount of points to use must not be null.");
        }
        if (usePoint.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The amount of points to use must not be negative.");
        }
        if (this.userPoint.compareTo(usePoint) < 0) {
            throw new UserPointBadRequestException(INSUFFICIENT_USER_POINT);
        }
        this.userPoint = this.userPoint.subtract(usePoint);
    }
}
