package com.example.tddecommerce.domain.product.infrastructure;

import com.example.tddecommerce.domain.product.business.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.delFlag = false")
    Optional<Product> findByIdAndNotDeleted(Long id);
}

