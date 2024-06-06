package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductStockReader {

    private final IProductStockRepository productStockRepository;
    private final IProductRepository productRepository;  // 상품 정보 리포지토리 추가

    public ProductStockReader(IProductStockRepository productStockRepository, IProductRepository productRepository) {
        this.productStockRepository = productStockRepository;
        this.productRepository = productRepository;
    }

    public ProductStock getProductStock(Long productId) {
        // 상품 정보 조회
        Optional<Product> product = productRepository.findByProductId(productId);
        if (!product.isPresent()) {
            throw new RuntimeException("Product not found for ID: " + productId);  // 상품이 없을 경우 예외 처리
        }

        // 재고 정보 조회
        Optional<ProductStock> stock = productStockRepository.findByProductId(productId);
        return stock.orElseGet(() -> new ProductStock(productId, 0));  // 상품 정보가 있는 새로운 재고 객체 반환
    }
}
