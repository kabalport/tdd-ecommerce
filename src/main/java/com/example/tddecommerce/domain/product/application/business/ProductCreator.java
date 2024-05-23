package com.example.tddecommerce.domain.product.application.business;

import com.example.tddecommerce.domain.product.domain.repository.ProductRepository;
import com.example.tddecommerce.domain.product.domain.model.Product;
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
