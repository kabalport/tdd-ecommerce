package com.example.tddecommerce.domain.order.infrastructure;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductOrderRepository implements IProductOrderRepository {
    private final IProductOrderJpaRepository productOrderJpaRepository;

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderJpaRepository.save(productOrder);
    }


}
