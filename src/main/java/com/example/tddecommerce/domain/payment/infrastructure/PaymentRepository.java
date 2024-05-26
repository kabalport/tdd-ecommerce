package com.example.tddecommerce.domain.payment.infrastructure;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.payment.business.repository.IPaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class PaymentRepository implements IPaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    public PaymentRepository(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Override
    public void save(Payment payment) {
        paymentJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findByOrder(ProductOrder order) {
        return paymentJpaRepository.findByOrder(order);
    }
}
