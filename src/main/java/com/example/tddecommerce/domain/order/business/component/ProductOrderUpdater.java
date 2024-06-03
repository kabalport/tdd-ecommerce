package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductOrderUpdater {

    private final IProductOrderRepository productOrderRepository;

    public void updateOrderStatus(Long orderId, ProductOrderStatus newStatus) {
        ProductOrder order = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setOrderStatus(newStatus);
        productOrderRepository.save(order);
    }

    public void updateOrder(Long orderId, ProductOrder updatedOrder) {
        ProductOrder existingOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        productOrderRepository.save(existingOrder);
    }
}
