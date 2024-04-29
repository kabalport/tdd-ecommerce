package com.example.tddecommerce.member.business.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_transaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointTransactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    private BigDecimal point;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactedAt;

    private PointTransaction(Member member, BigDecimal point) {
        this.member = member;
        this.point = point;
        transactedAt = LocalDateTime.now();
    }

    public static PointTransaction createByPayment(Member member, BigDecimal point) {
        PointTransaction pointTransaction = new PointTransaction(member, point);
        pointTransaction.transactionType = TransactionType.PAYMENT;
        return pointTransaction;
    }

    public static PointTransaction createByCharge(Member member, BigDecimal point) {
        PointTransaction pointTransaction = new PointTransaction(member, point);
        pointTransaction.transactionType = TransactionType.CHARGE;
        return pointTransaction;
    }
}
