package com.example.tddecommerce.order.business.repository;

import com.example.tddecommerce.order.business.model.ProductOrder;
import com.example.tddecommerce.order.infrastructure.IProductOrderJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductOrderCoreRepository implements IProductOrderRepository{
    private final IProductOrderJpaRepository productOrderJpaRepository;

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderJpaRepository.save(productOrder);
    }


}
