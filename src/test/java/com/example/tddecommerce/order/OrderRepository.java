package com.example.tddecommerce.order;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {

    private Map<Long, Order> persistence = new HashMap<>();

    private Long sequence = 0L;

    public Order save(Order order) {
        order.setOrderId(++sequence);
        persistence.put(order.getOrderId(), order);
        return order;
    }
}
