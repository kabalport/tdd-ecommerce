package com.example.tddecommerce.domain.product.business.component;


import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductUpdater {

    private final IProductRepository iproductRepository;

    public Product updateProduct(Product product, String name, BigDecimal price,String description, DiscountPolicy discountPolicy) {

        // 상품 정보 업데이트
        product.update(name, price, description, discountPolicy);

        // 상품을 데이터베이스에 저장
        return iproductRepository.save(product);
    }
}
