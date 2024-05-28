package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderUpdater {

    @Autowired
    private ProductStockService productStockService;

    public void updateStock(Long productId, int quantity) {
        ProductStock productStock = productStockService.getProductStock(null);
        productStockService.decreaseProductStock(productStock, quantity);
    }
}