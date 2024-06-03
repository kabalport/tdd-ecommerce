package com.example.tddecommerce.domain.order.application.service;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.ProductOrderCreator;
import com.example.tddecommerce.domain.order.business.component.ProductOrderItemCreator;
import com.example.tddecommerce.domain.order.business.component.ProductOrderReader;
import com.example.tddecommerce.domain.order.business.component.ProductOrderUpdater;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.component.TotalAmountCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 제품 주문을 관리하는 서비스 클래스.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderService {
    private final ProductOrderCreator productOrderCreator;
    private final ProductOrderReader productOrderReader;
    private final ProductOrderUpdater productOrderUpdater;
    private final ProductOrderItemCreator productOrderItemCreator;
    private final TotalAmountCalculator totalAmountCalculator;

    /**
     * 새 제품 주문을 생성하고 저장합니다.
     *
     * @param userId 주문을 하는 사용자의 ID.
     * @param items 주문 항목 목록.
     * @param totalAmount 주문에 대해 지불할 총 금액.
     * @return 생성된 ProductOrder.
     */
    public ProductOrder createOrder(Long userId, List<ProductOrderItem> items, BigDecimal totalAmount) {
        // 주문 생성 및 저장
        ProductOrder order;
        try {
            order = ProductOrder.builder()
                    .userId(userId)
                    .orderDate(LocalDate.now())
                    .orderStatus(ProductOrderStatus.PENDING)
                    .orderItems(items)
                    .totalPrice(totalAmount)
                    .build();

            productOrderCreator.saveOrder(order);
        } catch (Exception e) {
            log.error("주문 생성 중 오류 발생: ", e);
            throw e;
        }

        return order;
    }

    /**
     * 주문 ID로 주문을 조회합니다.
     *
     * @param orderId 조회할 주문의 ID.
     * @return 조회된 ProductOrder.
     */
    public ProductOrder getOrder(Long orderId) {
        return productOrderReader.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));
    }

    /**
     * 제품 주문 세부 정보에서 주문 항목을 준비합니다.
     *
     * @param productOrderDetails 제품 주문 세부 정보 목록.
     * @return 준비된 ProductOrderItem 목록.
     */
    public List<ProductOrderItem> prepareOrderItems(List<ProductOrderDetail> productOrderDetails) {
        return productOrderItemCreator.prepareOrderItems(productOrderDetails);
    }

    /**
     * 주문 항목에 대해 지불할 총 금액을 계산합니다.
     *
     * @param items 주문 항목 목록.
     * @return 지불할 총 금액.
     */
    public BigDecimal prepareAmountToBePaid(List<ProductOrderItem> items) {
        return totalAmountCalculator.calculateTotalAmount(items);
    }

    /**
     * 기존 주문의 상태를 업데이트합니다.
     *
     * @param orderId 업데이트할 주문의 ID.
     * @param newStatus 주문에 설정할 새 상태.
     */
    public void updateOrderStatus(Long orderId, ProductOrderStatus newStatus) {
        productOrderUpdater.updateOrderStatus(orderId, newStatus);
    }
}
