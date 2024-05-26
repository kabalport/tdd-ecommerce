package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductReader {

    private final IProductRepository iProductRepository;

    public ProductReader(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Optional<Product> selectOne(Long productId) {
        return iProductRepository.findByProductId(productId);
    }



}
