package com.example.tddecommerce.order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ProductStockRepository {

    private Map<Long, ProductStock> persistence = new HashMap<>();

    private Long sequence = 0L;

    public ProductStock save(ProductStock stock) {
        stock.setProductStockId(++sequence);
        persistence.put(stock.getProductStockId(), stock);
        return stock;
    }

    public Optional<ProductStock> findByProductId(long l) {
        return persistence.values().stream()
                .filter(stock -> stock.getProductId().equals(l))
                .findFirst();
    }
}
