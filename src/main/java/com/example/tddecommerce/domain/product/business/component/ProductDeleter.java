package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDeleter {

    private final ProductRepository productRepository;

    public void deleteProduct(Product product) {
        // 상품 삭제 플래그 설정
        product.setDelFlag(true);

        // 상품을 데이터베이스에 저장
        productRepository.save(product);
    }
}
