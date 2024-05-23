package com.example.tddecommerce.domain.product.application.business;

import com.example.tddecommerce.domain.product.domain.repository.ProductStockRepository;
import com.example.tddecommerce.domain.product.domain.model.ProductStock;
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
