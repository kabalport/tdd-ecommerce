package com.example.tddecommerce.domain.productstock.infrastructure;

import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductStockJpaRepository extends JpaRepository<ProductStock, Long> {
    @Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId ORDER BY ps.lastUpdated DESC")
    Optional<ProductStock> findByProductId(@Param("productId") Long productId);
}
