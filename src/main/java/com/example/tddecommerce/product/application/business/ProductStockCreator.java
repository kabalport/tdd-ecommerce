package com.example.tddecommerce.product.application.business;

import com.example.tddecommerce.product.domain.model.ProductStock;
import com.example.tddecommerce.product.domain.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStockCreator {

    private final ProductStockRepository productStockRepository;

    public void save(ProductStock stock) {
        productStockRepository.save(stock);
    }
}
