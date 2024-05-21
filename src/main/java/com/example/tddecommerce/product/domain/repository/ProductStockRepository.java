package com.example.tddecommerce.product.domain.repository;

import com.example.tddecommerce.product.domain.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
