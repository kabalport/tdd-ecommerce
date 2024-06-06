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


    public Payment createPayment(ProductOrder productOrder) {
        Payment payment = new Payment(productOrder.getTotalPrice(), productOrder);
        return paymentRepository.save(payment);
    }
}
