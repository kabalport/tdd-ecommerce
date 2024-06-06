package com.example.tddecommerce.domain.payment.business.model;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private ProductOrder productOrder;

    public Payment(BigDecimal amount, ProductOrder productOrder) {
        this.amount = amount;
        this.productOrder = productOrder;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
