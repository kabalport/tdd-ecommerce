package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductOrderValidator {

    private final UserService userService;
    private final ProductStockService productStockService;

    public void execute(Long userId, List<ProductOrderDetail> productOrderDetails) {
        // 사용자 유효성 검사
        User user = userService.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
        validateUser(user);

        // 주문 항목 유효성 검사
        for (ProductOrderDetail detail : productOrderDetails) {
            validateProductOrderDetail(detail);
        }
    }

    private void validateUser(User user) {

    }

    private void validateProductOrderDetail(ProductOrderDetail detail) {
        Product product = productStockService.getProduct(detail.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Invalid product ID: " + detail.getProductId());
        }
        if (product.isDelFlag()) {
            throw new IllegalArgumentException("Product is deleted: " + detail.getProductId());
        }
        if (detail.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity for product: " + detail.getProductId());
        }

        // 재고 유효성 검사
        int availableStock = productStockService.getProductStock(product.getId()).getQuantity();
        if (availableStock < detail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + detail.getProductId());
        }
    }
}
