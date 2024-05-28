package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderValidator {

    @Autowired
    private ProductStockService productStockService;

    public void validateStock(Long productId, int quantity) {
        ProductStock productStock = productStockService.getProductStock(null);
        if (productStock.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for product id: " + productId);
        }
    }
}