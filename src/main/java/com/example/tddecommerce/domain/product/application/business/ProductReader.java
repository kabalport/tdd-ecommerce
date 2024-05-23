package com.example.tddecommerce.domain.product.application.business;

import com.example.tddecommerce.domain.product.domain.repository.ProductRepository;
import com.example.tddecommerce.domain.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductRepository productRepository;

    public Optional<Product> selectOne(Long productId) {
        return productRepository.findById(productId);
    }
}
