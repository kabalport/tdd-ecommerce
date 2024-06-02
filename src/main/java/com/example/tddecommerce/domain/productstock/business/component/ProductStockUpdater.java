package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductStockUpdater {

    private final IProductStockRepository productStockRepository;

    private final IProductRepository productRepository;

    /**
     * 상품 재고를 설정하는 메서드
     * @param productId
     * @param newQuantity
     * @return
     */
    @Transactional
    public ProductStock setStock(Long productId, int newQuantity) {
        ProductStock productStock = productStockRepository.findByProductId(productId).orElseThrow(() -> new ProductException("No product stock found for product ID: " + productId));
        productStock.setQuantity(newQuantity);
        return productStockRepository.save(productStock);
    }
    /**
     * 상품 재고를 증가시키는 메서드
     * @param quantity 증가시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    public ProductStock increaseStock(Long productId, int quantity) {
        ProductStock productStock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductException("No product stock found for product ID: " + productId));
        if (quantity < 0 || quantity > 1000) {
            throw new ProductException("Invalid stock quantity. It must be between 0 and 1000.");
        }
        productStock.increase(quantity);
        return productStockRepository.save(productStock);
    }

    /**
     * 상품 재고를 감소시키는 메서드
     */
    @Transactional
    public ProductStock decreaseStock(Long productId, int quantity) {
        ProductStock productStock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductException("No product stock found for product ID: " + productId));
        if (productStock.getQuantity() < quantity) {
            throw new ProductException("재고는 0개 미만이 될 수 없습니다.");
        }
        productStock.decrease(quantity);
        return productStockRepository.save(productStock);
    }



}
