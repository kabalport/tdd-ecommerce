package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductStockUpdater {

    private final IProductStockRepository productStockRepository;

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
