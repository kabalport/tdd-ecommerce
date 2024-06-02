package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.application.CreateProductRequest;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductCreator {

    private final IProductRepository iProductRepository;

    public Product save(Product product) {
        return iProductRepository.save(product);
    }

    public void saveAllProducts(List<Product> productList) {
        iProductRepository.saveAll(productList);
    }

    //    CreateProductRequest request
    public Product execute(String name, BigDecimal price, String desc, DiscountPolicy policy) {
        Product product = new Product(name, price, desc, policy);
        return iProductRepository.save(product);
    }
}
