package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
import com.example.tddecommerce.domain.order.application.service.ProductOrderService;
import com.example.tddecommerce.domain.order.business.component.ProductOrderValidator;
import com.example.tddecommerce.domain.order.business.component.OrderRollbackHandler;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderUseCase {

    private final ProductOrderValidator productOrderValidator;
    private final OrderRollbackHandler orderRollbackHandler;
    private final ProductOrderService productOrderService;
    private final PaymentService paymentService;
    private final EmailService emailService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final ProductStockService productStockService;


    /**
     * 제품 주문을 시작한다
     * 서비스 앞에 파사드를 두어 파사드에서 서비스들을 관리한다
     * @param productOrderRequest 주문 요청 객체
     */
    @Transactional
    public void execute(ProductOrderRequest productOrderRequest) {
        Long userId = productOrderRequest.getUserId();
        List<ProductOrderDetail> productOrderDetails = productOrderRequest.getProductOrderDetails();
        BigDecimal pointsToUse = productOrderRequest.getPointsToUse();

        List<ProductOrderItem> items = null;
        Map<Product, ProductStock> productStockMap = null;
        User user = null;
        try {
            // 유저 조회
            user = userService.getUserById(userId);
            log.info("유저 조회 완료: userId={}", userId);

            // 주문 항목 준비
            items = productOrderService.prepareOrderItems(productOrderDetails);
            log.info("주문 항목 준비 완료: items={}", items);

            // 주문 항목 검증
            productOrderValidator.validateOrderItems(items);
            log.info("주문 항목 검증 완료");

            // 재고 검증 및 감소
            productStockMap = productStockService.manageProductStock(items);
            log.info("재고 검증 및 감소 완료");

            // 유저 포인트 검증 및 차감
            userPointService.handleUserPoints(userId, pointsToUse);
            log.info("유저 포인트 검증 및 차감 완료: pointsUsed={}", pointsToUse);

            // 결제 총금액 계산
            BigDecimal totalAmount = productOrderService.prepareAmountToBePaid(items);
            totalAmount = totalAmount.subtract(pointsToUse); // 포인트 사용 후 총 금액 계산
            log.info("결제 총금액 계산 완료: totalAmount={}", totalAmount);

            // 주문 검증
            productOrderValidator.validateOrder(user, items, totalAmount);
            log.info("주문 검증 완료");

            // 주문 생성
            ProductOrder order = productOrderService.createOrder(user.getUserId(), items, totalAmount);
            log.info("주문 생성 완료: orderId={}", order.getId());

            // 결제 처리
            paymentService.processPayment(order);
            log.info("결제 처리 완료");

            // 이메일 발송
            emailService.sendOrderConfirmationEmail(user.getEmail(), order);
            log.info("이메일 발송 완료: email={}", user.getEmail());

            log.info("해당 주문 완료: userId={}", userId);
        } catch (Exception e) {
            log.error("주문 에러: userId={}", userId, e);

            orderRollbackHandler.rollbackStockAndPoints(userId, items, productStockMap, pointsToUse);
            log.info("롤백 완료: userId={}, pointsToUse={}", userId, pointsToUse);

            throw new RuntimeException("주문 실패 보상 트랜잭션 발동", e);
        }
    }
}
