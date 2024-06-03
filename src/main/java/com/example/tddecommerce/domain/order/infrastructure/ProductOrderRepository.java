package com.example.tddecommerce.domain.order.infrastructure;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductOrderRepository implements IProductOrderRepository {
    private final IProductOrderJpaRepository productOrderJpaRepository;

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderJpaRepository.save(productOrder);
    }

    @Override
    public Optional<ProductOrder> findById(Long orderId) {
        return Optional.empty();
    }

    @Override
    public List<ProductOrder> findByUser(User user) {
        return null;
    }

    @Override
    public List<ProductOrder> findByOrderStatus(ProductOrderStatus status) {
        return null;
    }


}
