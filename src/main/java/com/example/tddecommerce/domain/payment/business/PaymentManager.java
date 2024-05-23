package com.example.tddecommerce.domain.payment.business;

import com.example.tddecommerce.domain.payment.PaymentRepository;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentManager {
    private final PaymentRepository paymentRepository;

    public boolean createPayment(ProductOrder order, BigDecimal amount, String paymentMethod) {
        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentDateTime(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.SUCCESS)
                .build();
        paymentRepository.save(payment);
        return true;
    }
}