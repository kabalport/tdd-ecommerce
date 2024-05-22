package com.example.tddecommerce.payment;

import com.example.tddecommerce.payment.business.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
