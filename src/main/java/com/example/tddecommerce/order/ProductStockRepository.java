package com.example.tddecommerce.order;

import java.util.HashMap;
import java.util.Map;

public class ProductStockRepository {

    private Map<Long, ProductStock> persistence = new HashMap<>();

    private Long sequence = 0L;

    public ProductStock save(ProductStock stock) {
        stock.setProductStockId(++sequence);
        persistence.put(stock.getProductStockId(), stock);
        return stock;
    }
}
