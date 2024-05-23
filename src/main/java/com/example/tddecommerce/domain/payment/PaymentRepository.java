package com.example.tddecommerce.domain.payment;

import com.example.tddecommerce.domain.payment.business.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
