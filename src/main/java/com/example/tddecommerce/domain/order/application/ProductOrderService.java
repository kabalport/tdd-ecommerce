package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.product.business.component.ProductReader;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockReader;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockUpdater;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointCharger;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointReader;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointValidator;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
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
public class ProductOrderService {

    private final ProductOrderCreator productOrderCreator;
    private final ProductReader productReader;
    private final ProductOrderValidator productOrderValidator;
    private final UserPointReader userPointReader;
    private final UserPointValidator userPointValidator;
    private final ProductStockReader productStockReader;
    private final ProductStockUpdater productStockUpdater;



    @Transactional
    public ProductOrder createOrder(Long userId, List<ProductOrderDetail> productOrderDetails, BigDecimal pointsToUse) {
        // 주문 요청 재고 검증
        productOrderValidator.validateStock(productOrderDetails);

        // 주문 항목 준비
        List<ProductOrderItem> items = productOrderDetails.stream().map(detail -> {
            Product product = productReader.selectOne(detail.getProductId()).orElseThrow(() -> new ProductException("Product not found: " + detail.getProductId()));
            return new ProductOrderItem(product, detail.getQuantity(), product.getPrice());
        }).collect(Collectors.toList());

        // 총 금액 계산
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 사용자 포인트 검증 및 차감
        UserPoint currentUserPoint = userPointReader.readByUserId(userId);
        userPointValidator.validatePurchase(currentUserPoint, pointsToUse);
        currentUserPoint.decreasePoint(pointsToUse);


        // 남은 결제 금액 계산
        BigDecimal amountToBePaid = totalAmount.subtract(pointsToUse);

        // 재고 확인 및 감소
        Map<Product, ProductStock> productStockMap = items.stream().collect(Collectors.toMap(
                ProductOrderItem::getProduct,
                item -> {
                    ProductStock productStock = productStockReader.getProductStock(item.getProduct());
                    if (productStock.getQuantity() < item.getQuantity()) {
                        throw new ProductException("Insufficient stock for product: " + item.getProduct().getId());
                    }
                    productStockUpdater.decreaseStock(productStock, item.getQuantity());
                    return productStock;
                }
        ));

        ProductOrder order = null;
        try {
            // 주문 생성
            order = ProductOrder.builder()
                    .userId(userId)
                    .orderDate(LocalDate.now())
                    .status(ProductOrderStatus.PENDING)
                    .items(items)
                    .totalAmount(totalAmount)
                    .amountToBePaid(amountToBePaid)
                    .build();

            // 주문 저장
            productOrderCreator.saveOrder(order);

        } catch (Exception e) {
            // 주문 생성 중 예외 발생 시 롤백 처리
            log.error("Error occurred while creating order: ", e);
            rollbackStockAndPoints(userId, pointsToUse, items, productStockMap);
            throw e;
        }

        // 주문 정보 반환
        return order;
    }

    private void rollbackStockAndPoints(Long userId, BigDecimal pointsToUse, List<ProductOrderItem> items, Map<Product, ProductStock> productStockMap) {
        log.info("Rolling back stock and points for user {}", userId);
        // 포인트 롤백
        UserPoint currentUserPoint = userPointReader.readByUserId(userId);
        currentUserPoint.addPoints(pointsToUse);

        // 재고 롤백
        items.forEach(item -> {
            ProductStock productStock = productStockMap.get(item.getProduct());
            productStockUpdater.increaseStock(productStock, item.getQuantity());
        });
    }
}
