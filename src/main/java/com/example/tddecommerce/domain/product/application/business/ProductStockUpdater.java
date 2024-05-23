package com.example.tddecommerce.domain.product.application.business;

import com.example.tddecommerce.domain.product.domain.repository.ProductStockRepository;
import com.example.tddecommerce.domain.product.application.service.ProductException;
import com.example.tddecommerce.domain.product.domain.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStockUpdater {

    private final ProductStockRepository productStockRepository;

    /**
     * 상품 재고를 증가시키는 메서드
     * @param productStock 상품 재고 객체
     * @param quantity 증가시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    public ProductStock increaseStock(ProductStock productStock, int quantity) {
        productStock.increase(quantity);
        return productStockRepository.save(productStock);
    }

    /**
     * 상품 재고를 감소시키는 메서드
     * @param productStock 상품 재고 객체
     * @param quantity 감소시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    public ProductStock decreaseStock(ProductStock productStock, int quantity) {
        if (productStock.getQuantity() < quantity) {
            throw new ProductException("재고는 0개 미만이 될 수 없습니다.");
        }
        productStock.decrease(quantity);
        return productStockRepository.save(productStock);
    }
}
