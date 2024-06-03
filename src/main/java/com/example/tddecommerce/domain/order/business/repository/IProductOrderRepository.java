package com.example.tddecommerce.domain.order.business.repository;


import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.user.business.domain.User;

import java.util.List;
import java.util.Optional;

public interface IProductOrderRepository {

    ProductOrder save(ProductOrder productOrder);


    Optional<ProductOrder> findById(Long orderId);

    List<ProductOrder> findByUser(User user);

    List<ProductOrder> findByOrderStatus(ProductOrderStatus status);
}
