package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductOrderReader {

    private final IProductOrderRepository iProductOrderRepository;

    public Optional<ProductOrder> getOrderById(Long orderId) {
        return iProductOrderRepository.findById(orderId);
    }

    public List<ProductOrder> getOrdersByUser(User user) {
        return iProductOrderRepository.findByUser(user);
    }

    public List<ProductOrder> getOrdersByStatus(ProductOrderStatus status) {
        return iProductOrderRepository.findByOrderStatus(status);
    }
}
