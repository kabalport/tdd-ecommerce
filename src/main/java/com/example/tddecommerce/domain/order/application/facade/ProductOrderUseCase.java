package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
import com.example.tddecommerce.domain.order.api.ProductOrderResponse;
import com.example.tddecommerce.domain.order.application.service.ProductOrderService;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderUseCase {

    private final ProductOrderService productOrderService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final ProductStockService productStockService;

    @Transactional
    public ProductOrderResponse execute(ProductOrderRequest productOrderRequest) {
        Long userId = productOrderRequest.getUserId();
        List<ProductOrderDetail> productOrderDetails = productOrderRequest.getProductOrderDetails();
        BigDecimal pointsToUse = productOrderRequest.getPointsToUse();

        List<ProductOrderItem> items = null;
        User user;
        ProductOrder order;
        BigDecimal totalAmount;

        try {
            // 유저 조회
            user = userService.getUser(userId);
            log.info("유저 조회 완료: userId={}", userId);


            // 주문 항목 준비
            items = productOrderService.prepareOrderItems(productOrderDetails);
            log.info("주문 항목 준비 완료: items={}", items);

            // 재고 검증 및 감소
            productStockService.validateAndDecreaseStock(items);
            log.info("재고 검증 및 감소 완료");

            // 유저 포인트 검증 및 차감
            userPointService.useUserPoint(userId, pointsToUse);
            log.info("유저 포인트 검증 및 차감 완료: pointsUsed={}", pointsToUse);

            // 주문 총금액 계산
            totalAmount = productOrderService.prepareAmountToBePaid(items);
            totalAmount = totalAmount.subtract(pointsToUse); // 포인트 사용 후 총 금액 계산
            log.info("결제 총금액 계산 완료: totalAmount={}", totalAmount);


            // 주문 생성
            order = productOrderService.createOrder(user.getUserId(), items, totalAmount);
            log.info("주문 생성 완료: orderId={}", order.getId());

            // 결제 처리
            paymentService.processPayment(order);
            log.info("결제 처리 완료");


            log.info("해당 주문 완료: userId={}", userId);
            // 응답 생성
            return createProductOrderResponse(order, items, totalAmount);

        } catch (Exception e) {
            log.error("주문 에러: userId={}", userId, e);

            if (items != null) {
//                orderRollbackHandler.rollbackStockAndPoints(userId, items, pointsToUse);
                log.info("롤백 완료: userId={}, pointsToUse={}", userId, pointsToUse);
            }

            throw new RuntimeException("주문 실패 보상 트랜잭션 발동", e);
        }
    }

    private ProductOrderResponse createProductOrderResponse(ProductOrder order, List<ProductOrderItem> items, BigDecimal totalAmount) {
        List<ProductOrderResponse.OrderItem> orderItems = items.stream()
                .map(item -> ProductOrderResponse.OrderItem.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return ProductOrderResponse.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus().toString())
                .items(orderItems)
                .totalAmount(totalAmount)
                .build();
    }
}
