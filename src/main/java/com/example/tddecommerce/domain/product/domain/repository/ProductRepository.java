package com.example.tddecommerce.domain.product.domain.repository;

import com.example.tddecommerce.domain.product.application.business.IProductRepository;
import com.example.tddecommerce.domain.product.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements IProductRepository {

    private final ProductJpaRepository productJpaRepository;



}
