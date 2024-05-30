package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.component.*;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockManager;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
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
    private final ProductStockManager productStockManager;
    private final TotalAmountCalculator totalAmountCalculator;

    @Transactional
    public ProductOrder createOrder(Long userId, List<ProductOrderItem> items, BigDecimal totalAmount) {
        // 재고 확인 및 감소
        Map<Product, ProductStock> productStockMap = productStockManager.manageProductStock(items);

        // 주문 생성 및 저장
        ProductOrder order;
        try {
            order = ProductOrder.builder()
                    .userId(userId)
                    .orderDate(LocalDate.now())
                    .status(ProductOrderStatus.PENDING)
                    .items(items)
                    .totalAmount(totalAmount)
                    .build();

            productOrderCreator.saveOrder(order);
        } catch (Exception e) {
            log.error("Error occurred while creating order: ", e);
            throw e;
        }

        return order;
    }

    public List<ProductOrderItem> prepareOrderItems(List<ProductOrderDetail> productOrderDetails) {
        return productOrderItemCreator.prepareOrderItems(productOrderDetails);
    }

    public BigDecimal prepareAmountToBePaid(List<ProductOrderItem> items) {
        BigDecimal totalAmount = totalAmountCalculator.calculateTotalAmount(items);
        return null;
    }
}
