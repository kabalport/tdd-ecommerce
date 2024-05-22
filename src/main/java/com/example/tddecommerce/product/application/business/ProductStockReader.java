package com.example.tddecommerce.product.application.business;

import com.example.tddecommerce.product.application.service.ProductException;
import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.model.ProductStock;
import com.example.tddecommerce.product.domain.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStockReader {
    private final ProductStockRepository productStockRepository;

    /**
     * 주어진 상품에 대한 재고 정보를 조회합니다.
     * @param product 조회할 상품
     * @return 조회된 상품 재고 객체
     */
    public ProductStock getProductStock(Product product) {
        return productStockRepository.findByProduct(product)
                .orElseThrow(() -> new ProductException("Product stock not found for product id: " + product.getId()));
    }
}
