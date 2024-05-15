package com.example.tddecommerce.userpoint.business.domain;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class PointTransaction {
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

}