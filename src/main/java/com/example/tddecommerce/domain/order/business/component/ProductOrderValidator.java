package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
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


    public void validateOrderItems(List<ProductOrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("주문 항목이 비어 있습니다.");
        }

        for (ProductOrderItem item : items) {
            if (item.getProduct() == null) {
                throw new RuntimeException("주문 항목에 유효하지 않은 제품이 포함되어 있습니다.");
            }
            if (item.getQuantity() <= 0) {
                throw new RuntimeException("주문 항목의 수량이 유효하지 않습니다.");
            }
        }
    }

    public void validateOrder(User user, List<ProductOrderItem> items, BigDecimal totalAmount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("총 결제 금액이 유효하지 않습니다.");
        }
        // 추가적인 주문 전체에 대한 검증 로직이 필요하면 여기에 추가합니다.
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }

        if (!user.isActive()) {
            throw new RuntimeException("유저가 비활성화 상태입니다.");
        }

        if (!user.canPlaceOrder()) {
            throw new RuntimeException("유저에게 주문 권한이 없습니다.");
        }
    }

    public void validateOrderRequest(ProductOrderRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("유저 ID가 필요합니다.");
        }

        if (request.getProductOrderDetails() == null || request.getProductOrderDetails().isEmpty()) {
            throw new RuntimeException("적어도 하나의 주문 항목이 필요합니다.");
        }

        if (request.getPointsToUse() == null) {
            throw new RuntimeException("사용할 포인트가 필요합니다.");
        }

        if (request.getPointsToUse().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("포인트는 음수가 될 수 없습니다.");
        }
    }
}