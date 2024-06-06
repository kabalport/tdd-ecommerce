package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductReader {

    private final IProductRepository iProductRepository;

    public ProductReader(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Optional<Product> execute(Long productId) {
        Optional<Product> product = iProductRepository.findByProductId(productId);
        return product.filter(p -> !p.isDelFlag());
    }

    public Product getProduct(Long productId) {
        return execute(productId).orElseThrow(() -> new IllegalArgumentException("Product not found or is deleted: " + productId));
    }
}
