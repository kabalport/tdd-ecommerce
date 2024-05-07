package com.example.tddecommerce.order.business;

import com.example.tddecommerce.order.business.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductStockRepository extends JpaRepository<ProductStock,Long> {
    Optional<ProductStock> findByProductId(long productId);
}
