package com.example.tddecommerce.domain.product.infrastructure;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.infrastructure.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements IProductRepository {

    private final ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByProductId(Long productId) {
        return productJpaRepository.findByIdAndNotDeleted(productId);
    }

    @Override
    public void saveAll(List<Product> productList) {
        productJpaRepository.saveAll(productList);
    }
}
