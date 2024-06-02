package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRollbackHandler {

    private final UserPointService userPointService;
    private final ProductStockService productStockService;


    public void rollbackStockAndPoints(Long userId, List<ProductOrderItem> items, BigDecimal pointsToUse) {
        log.info("Rolling back stock and points for user {}", userId);
        UserPoint currentUserPoint = userPointService.getUserPoint(userId);
        currentUserPoint.addPoints(pointsToUse);

        items.forEach(item -> {
//            ProductStock productStock = productStockMap.get(item.getProduct());
//            productStockService.increaseProductStock(productStock, item.getQuantity());
        });
    }
}
