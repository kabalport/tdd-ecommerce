package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRollbackHandler {

    private final UserPointsHandler userPointsHandler;
    private final ProductStockManager productStockManager;

    public void rollbackStockAndPoints(Long userId,  List<ProductOrderItem> items, Map<Product, ProductStock> productStockMap,BigDecimal pointsToUse) {
        log.info("Rolling back stock and points for user {}", userId);
        UserPoint currentUserPoint = userPointsHandler.getUserPoints(userId);
        currentUserPoint.addPoints(pointsToUse);

        items.forEach(item -> {
            ProductStock productStock = productStockMap.get(item.getProduct());
            productStockManager.increaseStock(productStock, item.getQuantity());
        });


    }

    public void rollbackStockAndPoints(Long userId, List<ProductOrderItem> items, Map<Product, ProductStock> productStockMap) {

    }
}
