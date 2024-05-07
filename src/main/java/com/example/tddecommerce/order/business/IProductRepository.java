package com.example.tddecommerce.order.business;

import com.example.tddecommerce.order.business.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByProductId(long productId);
}
