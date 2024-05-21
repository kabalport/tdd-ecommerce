package com.example.tddecommerce.product.application.business;

import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.repository.ProductRepository;
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
