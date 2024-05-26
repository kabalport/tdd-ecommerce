package com.example.tddecommerce.domain.productstock.business.repository;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;

import java.util.Optional;

public interface IProductStockRepository {
    ProductStock save(ProductStock productStock);

    Optional<ProductStock> findByProduct(Product product);
}
