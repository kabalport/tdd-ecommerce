package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
import com.example.tddecommerce.domain.order.application.service.ProductOrderService;
import com.example.tddecommerce.domain.order.business.component.OrderRollbackHandler;
import com.example.tddecommerce.domain.order.business.component.ProductOrderValidator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductOrderUseCase {
    private final ProductOrderService productOrderService;
    private final PaymentService paymentService;

    private final ProductStockService productStockService;
    private final ProductOrderValidator productOrderValidator;
    private final OrderRollbackHandler orderRollbackHandler;

    public ProductOrderResult execute(ProductOrderRequest productOrderRequest) {
        Long userId = productOrderRequest.getUserId();
        List<ProductOrderDetail> productOrderDetails = productOrderRequest.getProductOrderDetails();
        List<ProductOrderItem> items = null;
        ProductOrder order = null;
        Payment payment = null;
        try {
            // 검증
            productOrderValidator.execute(userId, productOrderDetails);
            log.info("주문 검증 완료: userId={}", userId);
            // 주문 항목 생성과 감소 처리
            items = productOrderService.processOrderItem(productOrderDetails);
            log.info("주문 항목 생성 및 재고 감소 처리 완료: items={}", items);
            // 재고 검증 및 감소
            for (ProductOrderItem item : items) {
                productStockService.validateAndDecreaseStock(item.getProduct(), item.getQuantity());
            }
            // 주문 생성 - 주문 항목 재고 감소 처리와 주문 총 금액 계산을 주문에서 처리
            order = productOrderService.processOrder(userId, items);
            log.info("주문 생성 완료: orderId={}", order.getId());
            // 주문 결제 처리
            payment = paymentService.executePay(userId, order.getTotalPrice());
            log.info("주문 결제 처리 완료: paymentId={}", payment.getId());
            // 응답 생성
            return new ProductOrderResult(order, payment);
        } catch (Exception e) {
            log.error("주문 에러: userId={}", userId, e);
            if (items != null) {
                orderRollbackHandler.execute(order, payment);
                log.info("롤백 완료: orderId={}, paymentId={}", order != null ? order.getId() : null, payment != null ? payment.getId() : null);
            }
            throw new RuntimeException("주문 실패 보상 트랜잭션 발동", e);
        }
    }
}
