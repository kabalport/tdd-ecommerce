package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductOrderCreator {
    private final IProductOrderRepository iProductOrderRepository;

    public ProductOrder saveOrder(ProductOrder order) {
        iProductOrderRepository.save(order);
        return order;
    }
}
