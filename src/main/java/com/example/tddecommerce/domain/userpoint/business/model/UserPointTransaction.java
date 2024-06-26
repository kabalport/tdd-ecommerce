package com.example.tddecommerce.domain.userpoint.business.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class UserPointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointTransactionId;

    @ManyToOne
    @JoinColumn(name = "user_point_id", referencedColumnName = "userPointId")
    private UserPoint userPoint;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal changeAmount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, length = 50)
    private String transactionType;

    private String description;
    public UserPointTransaction(UserPoint userPoint, BigDecimal changeAmount, String transactionType, String description, LocalDateTime transactionDate) {
        this.userPoint = userPoint;
        this.changeAmount = changeAmount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public UserPointTransaction() {

    }
}