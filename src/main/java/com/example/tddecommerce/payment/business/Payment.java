package com.example.tddecommerce.payment.business;

import com.example.tddecommerce.order.business.model.ProductOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ecommerce_payment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ecommerce_payment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private ProductOrder order;

    // 결제 금액
    @Column(name = "ecommerce_payment_paymentMoney")
    private BigDecimal amount;

    // 결제 날짜와 시간
    @Column(name = "ecommerce_payment_date_time")
    private LocalDateTime paymentDateTime;

    // 결제수단
    private String paymentMethod;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "ecommerce_payment_status")
    private PaymentStatus status;
}

