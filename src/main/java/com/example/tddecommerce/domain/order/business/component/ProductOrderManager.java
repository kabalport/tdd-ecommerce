package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductOrderManager {

    private final IProductOrderRepository productOrderRepository;

    public void saveOrder(ProductOrder order) {
        productOrderRepository.save(order);
    }



}
