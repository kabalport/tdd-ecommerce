package com.example.tddecommerce.domain.product.application.business;

import org.springframework.stereotype.Component;

@Component
public class ProductReader {

    private final IProductRepository iProductRepository;

    public ProductReader(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }




//    public Optional<Product> selectOne(Long productId) {
//        return productRepository.findById(productId);
//    }
}
