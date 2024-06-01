package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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

    public void validateStock(List<ProductOrderDetail> productOrderDetails) {

    }

    public void validateOrder(User user, List<ProductOrderItem> items, BigDecimal totalAmount) {

    }

    public void validateOrderItems(List<ProductOrderItem> items) {

    }
}