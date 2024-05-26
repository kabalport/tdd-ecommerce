package com.example.tddecommerce.domain.productstock.application.repository;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.model.ProductStock;

import java.util.Optional;

public interface IProductStockRepository {
    ProductStock save(ProductStock productStock);

    Optional<ProductStock> findByProduct(Product product);
}
