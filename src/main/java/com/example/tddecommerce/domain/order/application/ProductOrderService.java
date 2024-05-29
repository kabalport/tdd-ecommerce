package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.*;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockManager;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.userpoint.business.component.UserPointsHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductOrderCreator productOrderCreator;
    private final ProductOrderValidator productOrderValidator;
    private final ProductOrderItemCreator productOrderItemCreator;
    private final TotalAmountCalculator totalAmountCalculator;
    private final UserPointsHandler userPointsHandler;
    private final ProductStockManager productStockManager;
    private final OrderRollbackHandler orderRollbackHandler;

    @Transactional
    public ProductOrder createOrder(Long userId, List<ProductOrderDetail> productOrderDetails, BigDecimal pointsToUse) {
        // 재고 검증
        productOrderValidator.validateStock(productOrderDetails);

        // 주문 항목 준비
        List<ProductOrderItem> items = productOrderItemCreator.prepareOrderItems(productOrderDetails);

        // 총 금액 계산
        BigDecimal totalAmount = totalAmountCalculator.calculateTotalAmount(items);

        // 포인트 검증 및 차감
        userPointsHandler.handleUserPoints(userId, pointsToUse);

        // 재고 확인 및 감소
        Map<Product, ProductStock> productStockMap = productStockManager.manageProductStock(items);

        // 주문 생성 및 저장
        ProductOrder order;
        try {
            BigDecimal amountToBePaid = totalAmount.subtract(pointsToUse);

            order = ProductOrder.builder()
                    .userId(userId)
                    .orderDate(LocalDate.now())
                    .status(ProductOrderStatus.PENDING)
                    .items(items)
                    .totalAmount(totalAmount)
                    .amountToBePaid(amountToBePaid)
                    .build();

            productOrderCreator.saveOrder(order);
        } catch (Exception e) {
            log.error("Error occurred while creating order: ", e);
            orderRollbackHandler.rollbackStockAndPoints(userId, pointsToUse, items, productStockMap);
            throw e;
        }

        return order;
    }
}
