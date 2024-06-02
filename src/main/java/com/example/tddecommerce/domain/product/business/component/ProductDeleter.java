package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleter {

    private final IProductRepository iProductRepository;

    public ProductDeleter(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public void deleteProduct(Product product) {
        product.setDelFlag(true);
        iProductRepository.save(product);
    }
}
