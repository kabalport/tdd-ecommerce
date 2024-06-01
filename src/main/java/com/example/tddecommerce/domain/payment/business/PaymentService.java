package com.example.tddecommerce.domain.payment.business;

import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.payment.business.component.PaymentCreator;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentCreator paymentCreator;
    private final ProductOrderCreator productOrderCreator;
    private final UserPointReader userPointReader;

    public void processPayment(ProductOrder productOrder) {
        try {
            // 결제 처리
            processPaymentTransaction(productOrder, productOrder.getAmountToBePaid());

            // 주문 상태 업데이트
            productOrder.setStatus(ProductOrderStatus.PAID);
            productOrderCreator.saveOrder(productOrder);

        } catch (Exception e) {
            log.error("Error occurred while processing order: ", e);
            throw e;
        }
    }

    private void processPaymentTransaction(ProductOrder productOrder, BigDecimal amountToBePaid) {
        boolean paymentSuccess = paymentCreator.createPayment(productOrder, amountToBePaid, "신용카드");
        if (!paymentSuccess) {
            throw new ProductException("Payment failed for order: " + productOrder.getId());
        }
    }


}
