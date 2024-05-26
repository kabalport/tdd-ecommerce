package com.example.tddecommerce.domain.productstock.business.component;

import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductStockCreator {

    private final IProductStockRepository iProductStockRepository;

    public void save(ProductStock stock) {
        iProductStockRepository.save(stock);
    }

    public Optional<ProductStock> findByProduct(Product result) {
        return iProductStockRepository.findByProduct(result);
    }
}
