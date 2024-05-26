package com.example.tddecommerce.domain.productstock.infrastructure;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.repository.IProductStockRepository;
import com.example.tddecommerce.domain.productstock.application.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductStockRepository implements IProductStockRepository {

    private final ProductStockJpaRepository productStockJpaRepository;

    @Override
    public ProductStock save(ProductStock productStock) {
        return productStockJpaRepository.save(productStock);
    }

    @Override
    public Optional<ProductStock> findByProduct(Product product) {
        return productStockJpaRepository.findByProduct(product);
    }
}
