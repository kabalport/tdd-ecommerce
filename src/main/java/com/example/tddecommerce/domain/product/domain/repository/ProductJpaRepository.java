package com.example.tddecommerce.domain.product.domain.repository;

import com.example.tddecommerce.domain.product.domain.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
