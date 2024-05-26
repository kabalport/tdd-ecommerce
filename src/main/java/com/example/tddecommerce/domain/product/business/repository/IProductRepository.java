package com.example.tddecommerce.domain.product.business.repository;

import com.example.tddecommerce.domain.product.business.model.Product;

import java.util.Optional;

public interface IProductRepository {


    Product save(Product product);

    Optional<Product> findByProductId(Long productId);
}
