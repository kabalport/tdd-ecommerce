package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.component.ProductReader;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductOrderItemCreator {
    private final ProductReader productReader;
    private final IProductStockRepository iProductStockRepository;

    public List<ProductOrderItem> prepareOrderItems(List<ProductOrderDetail> productOrderDetails) {
        return productOrderDetails.stream().map(detail -> {
            Product product = productReader.execute(detail.getProductId()).orElseThrow(() -> new ProductException("Product not found: " + detail.getProductId()));
            return new ProductOrderItem(product, detail.getQuantity(), product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
        }).collect(Collectors.toList());
    }

    public List<ProductOrderItem> createOrderItems(List<ProductOrderDetail> productOrderDetails) {
        return productOrderDetails.stream().map(detail -> {
            // 제품 정보 가져오기
            Product product = productReader.getProduct(detail.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Invalid product ID: " + detail.getProductId());
            }

            // ProductOrderItem 생성
            return ProductOrderItem.builder()
                    .product(product)
                    .quantity(detail.getQuantity())
                    .price(product.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }
}

