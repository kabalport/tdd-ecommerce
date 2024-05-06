package com.example.tddecommerce.userPoint.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "user_point")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private BigDecimal point;

    public UserPoint() {

    }
    public UserPoint(String userId, BigDecimal point) {
        this.userId = userId;
        this.point = point;
    }


    public void decrease(BigDecimal totalAmount) {
        if (this.point.compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("Insufficient points");
        }
        this.point = this.point.subtract(totalAmount);
    }
}
