package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreator {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
