package com.example.tddecommerce.domain.productstock.infrastructure;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductStockJpaRepository extends JpaRepository<ProductStock, Long> {
    @Query("SELECT ps FROM ProductStock ps WHERE ps.product = :product")
    Optional<ProductStock> findByProduct(Product product);
}
