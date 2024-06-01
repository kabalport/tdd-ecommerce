package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.component.ProductReader;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductOrderItemCreator {
    private final ProductReader productReader;

    public List<ProductOrderItem> prepareOrderItems(List<ProductOrderDetail> productOrderDetails) {
        return productOrderDetails.stream().map(detail -> {
            Product product = productReader.selectOne(detail.getProductId())
                    .orElseThrow(() -> new ProductException("Product not found: " + detail.getProductId()));
            return new ProductOrderItem(product, detail.getQuantity(), product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
        }).collect(Collectors.toList());
    }
}

