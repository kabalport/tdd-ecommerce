package com.example.tddecommerce.product.application.business;

import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.repository.ProductRepository;
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
