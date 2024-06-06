package com.example.tddecommerce.domain.payment.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.payment.business.model.PaymentStatus;
import com.example.tddecommerce.domain.payment.business.repository.IPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentCreator {
    private final IPaymentRepository paymentRepository;

    public PaymentCreator(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(Long userId, BigDecimal amount) {
        Payment payment = Payment.builder()
                .userId(userId)
                .amount(amount)
                .status("PAID")
                .build();

        return paymentRepository.save(payment);
    }
}
