package com.example.tddecommerce.domain.productstock.application;

import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.product.business.component.ProductReader;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockReader;
import com.example.tddecommerce.domain.productstock.business.component.ProductStockUpdater;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductReader productReader;
    private final ProductStockReader productStockReader;
    private final ProductStockUpdater productStockUpdater;

    public Product getProduct(Long productId) {
        return productReader.getProduct(productId);
    }

    /**
     * 주어진 상품에 대한 재고 정보를 조회합니다.
     * @param productId 조회할 상품의 ID
     * @return 조회된 상품 재고 객체
     */
    public ProductStock getProductStock(Long productId) {
        return productStockReader.getProductStock(productId);
    }

    /**
     * 주어진 상품 재고를 증가시킵니다.
     * @param productStock 상품 재고 객체
     * @param quantity 증가시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    public ProductStock increaseProductStock(ProductStock productStock, int quantity) {
        return productStockUpdater.increaseStock(productStock.getProductId(), quantity);
    }

    /**
     * 주어진 상품 재고를 감소시킵니다.
     * @param productStock 상품 재고 객체
     * @param quantity 감소시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    public ProductStock decreaseProductStock(ProductStock productStock, int quantity) {
        return productStockUpdater.decreaseStock(productStock.getProductId(), quantity);
    }

    public void validateAndDecreaseStock(List<ProductOrderItem> items) {
        for (ProductOrderItem item : items) {
            ProductStock productStock = getProductStock(item.getProductId());
            decreaseProductStock(productStock, item.getQuantity());
        }
    }
}
