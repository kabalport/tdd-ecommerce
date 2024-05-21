package com.example.tddecommerce.product.application.business;


import com.example.tddecommerce.product.application.service.ProductValidator;
import com.example.tddecommerce.product.domain.model.DiscountPolicy;
import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductUpdater {

    private final ProductRepository productRepository;

    public Product updateProduct(Product product, String name, BigDecimal price,String description, DiscountPolicy discountPolicy) {

        // 상품 정보 업데이트
        product.update(name, price, description, discountPolicy);

        // 상품을 데이터베이스에 저장
        return productRepository.save(product);
    }
}
