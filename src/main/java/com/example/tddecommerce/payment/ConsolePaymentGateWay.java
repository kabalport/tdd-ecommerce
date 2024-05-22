package com.example.tddecommerce.payment;

import com.cdy.ecommerce.domain.payment.business.Payment;

public class ConsolePaymentGateWay implements PaymentGateway{
    @Override
    public void execute(Payment payment) {
        System.out.println("결제완료");
    }
}
