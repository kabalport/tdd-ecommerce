package com.example.tddecommerce.domain.payment.business;

import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.payment.business.component.PaymentCreator;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import jakarta.transaction.Transactional;
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

    public void processPayment(ProductOrder productOrder) {
        try {
            // 결제 처리

            // 유저 포인트 차감
//            userPointService.useUserPoint(userId, pointsToUse);

            // 주문 상태 업데이트
            productOrder.setOrderStatus(ProductOrderStatus.PAID);
            productOrderCreator.saveOrder(productOrder);

        } catch (Exception e) {
            log.error("Error occurred while processing order: ", e);
            throw e;
        }
    }


    @Transactional
    public Payment executePay(Long userId, BigDecimal amount) {
        return paymentCreator.createPayment(userId, amount);
    }

}
