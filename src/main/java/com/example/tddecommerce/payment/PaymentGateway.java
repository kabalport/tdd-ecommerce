package com.example.tddecommerce.payment;

import com.cdy.ecommerce.domain.payment.business.Payment;

public interface PaymentGateway {
    void execute(Payment payment);
}