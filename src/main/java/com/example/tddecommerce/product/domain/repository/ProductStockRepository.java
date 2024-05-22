package com.example.tddecommerce.product.domain.repository;

import com.example.tddecommerce.product.domain.model.Product;
import com.example.tddecommerce.product.domain.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    Optional<ProductStock> findByProduct(Product product);
}
