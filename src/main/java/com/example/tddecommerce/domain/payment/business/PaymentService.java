package com.example.tddecommerce.domain.payment.business;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.component.PaymentCreator;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentCreator paymentCreator;

    @Transactional
    public Payment executePay(ProductOrder productOrder) {
        // 결제 생성
        return paymentCreator.createPayment(productOrder);
    }

}
