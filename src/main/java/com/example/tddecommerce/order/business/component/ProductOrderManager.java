package com.example.tddecommerce.order.business.component;

import com.example.tddecommerce.order.business.model.ProductOrder;
import com.example.tddecommerce.order.business.repository.IProductOrderRepository;
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
