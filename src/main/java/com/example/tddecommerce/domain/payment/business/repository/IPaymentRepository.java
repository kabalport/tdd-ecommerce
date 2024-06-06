package com.example.tddecommerce.domain.payment.business.repository;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;

import java.util.Optional;

public interface IPaymentRepository {
    Payment save(Payment payment);
//    Optional<Payment> findByOrder(ProductOrder order); // 추가된 메서드

}
