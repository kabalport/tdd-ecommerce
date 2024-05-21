package com.example.tddecommerce.product.domain.repository;

import com.example.tddecommerce.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
