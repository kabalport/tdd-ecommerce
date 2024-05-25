package com.example.tddecommerce.domain.order.usecase;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.business.component.ProductOrderManager;
import com.example.tddecommerce.domain.order.business.component.ProductOrderValidate;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.payment.business.PaymentManager;
import com.example.tddecommerce.domain.product.application.business.ProductReader;
import com.example.tddecommerce.domain.product.application.business.ProductStockReader;
import com.example.tddecommerce.domain.product.application.business.ProductStockUpdater;
import com.example.tddecommerce.domain.product.application.service.ProductException;
import com.example.tddecommerce.domain.product.domain.model.Product;
import com.example.tddecommerce.domain.product.domain.model.ProductStock;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.controller.ProductOrderDTO;
import com.example.tddecommerce.domain.user.business.component.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderAndPayUseCase {
    private final ProductOrderValidate productOrderValidate;
    private final UserPointValidator userPointValidator;
    private final UserReader userReader;
    private final UserPointReader userPointReader;
    private final ProductOrderManager productOrderManager;
    private final ProductReader productReader;
    private final PaymentManager paymentManager;
    private final ProductStockUpdater productStockUpdater;
    private final ProductStockReader productStockReader;
    private final EmailService emailService;

    @Transactional
    public ProductOrder execute(ProductOrderDTO.Request request) {
        ProductOrder order = null;
        List<ProductOrderItem> items = null;
        try {
            // 주문 요청 유효성 검증
            productOrderValidate.validate(request);

            // 유저 조회
            User user = userReader.readByUserId(request.getUserId());

            // 주문 항목 준비
            items = request.getProducts().stream().map(detail -> {
                Product product = productReader.selectOne(detail.getProductId())
                        .orElseThrow(() -> new ProductException("Product not found: " + detail.getProductId()));
                return new ProductOrderItem(product, detail.getQuantity(), product.getPrice());
            }).collect(Collectors.toList());

            // 재고 검사 및 저장
            Map<Product, ProductStock> productStockMap = items.stream().collect(Collectors.toMap(
                    ProductOrderItem::getProduct,
                    item -> {
                        ProductStock productStock = productStockReader.getProductStock(item.getProduct());
                        if (productStock.getQuantity() < item.getQuantity()) {
                            throw new ProductException("Insufficient stock for product: " + item.getProduct().getId());
                        }
                        return productStock;
                    }
            ));
            log.info("Stock availability checked for all items");

            // 총 금액 계산
            BigDecimal totalPurchaseAmount = items.stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 사용자 포인트 정보 조회 및 검증
            UserPoint currentUserPoint = userPointReader.readByUserId(user.getUserId());
            userPointValidator.validatePurchase(currentUserPoint, totalPurchaseAmount);

            // 포인트 차감
            currentUserPoint.decreasePoint(totalPurchaseAmount);

            // 주문 생성
            order = ProductOrder.builder()
                    .member(user)
                    .orderDate(LocalDate.now())
                    .status(ProductOrderStatus.PENDING)
                    .items(items)
                    .build();

            // 주문 저장
            productOrderManager.saveOrder(order);

            // 결제 생성 및 상태 확인
            boolean paymentSuccess = paymentManager.createPayment(order, totalPurchaseAmount, "결제수단");
            if (paymentSuccess) {
                order.setStatus(ProductOrderStatus.PAID);
            } else {
                throw new ProductException("Payment failed for order: " + order.getId());
            }
            productOrderManager.saveOrder(order);

            // 재고 감소
            items.forEach(item -> {
                ProductStock productStock = productStockMap.get(item.getProduct());
                productStockUpdater.decreaseStock(productStock, item.getQuantity());
            });

            // 데이터 플랫폼으로 주문 정보 전송
            sendDataToDataPlatform(order);

            // 이메일 알림 전송
            sendOrderConfirmation(user, order);

            // 주문 정보 반환
            return order;
        } catch (Exception e) {
            log.error("Error occurred while processing order: ", e);
            if (order != null && items != null) {
                rollbackStockAndPoints(order, items);
            }
            throw e;
        }
    }

    private void rollbackStockAndPoints(ProductOrder order, List<ProductOrderItem> items) {
        log.info("Rolling back stock and points for order {}", order.getId());
        // 포인트 롤백
        UserPoint currentUserPoint = userPointReader.readByUserId(order.getMember().getUserId());
        BigDecimal totalPurchaseAmount = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        currentUserPoint.addPoints(totalPurchaseAmount);

        // 재고 롤백
        items.forEach(item -> {
            ProductStock productStock = productStockReader.getProductStock(item.getProduct());
            productStockUpdater.increaseStock(productStock, item.getQuantity());
        });
    }

    private void sendDataToDataPlatform(ProductOrder order) {
        log.info("Sending order information to data platform for order {}", order.getId());
        // 결제 성공 시 데이터 플랫폼으로 주문 정보를 전송하는 로직 구현
    }

    private void sendOrderConfirmation(User user, ProductOrder order) {
        emailService.sendOrderConfirmationEmail(user, order);
        log.info("Order confirmation email sent to user {}", user.getUserId());
    }
}
