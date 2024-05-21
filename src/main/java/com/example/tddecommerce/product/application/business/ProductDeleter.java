package com.example.tddecommerce.product.application.business;

import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.repository.ProductRepository;
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
