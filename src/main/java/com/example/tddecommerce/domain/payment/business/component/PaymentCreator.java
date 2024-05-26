package com.example.tddecommerce.domain.payment.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.payment.business.model.PaymentStatus;
import com.example.tddecommerce.domain.payment.business.repository.IPaymentRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentCreator {
    private final IPaymentRepository paymentRepository;

    public PaymentCreator(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public boolean createPayment(ProductOrder order, BigDecimal amount, String paymentMethod) {
        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentDateTime(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.SUCCESS)
                .build();
        paymentRepository.save(payment);
        return payment.getStatus() == PaymentStatus.SUCCESS;
    }
}
