package com.example.tddecommerce.domain.order.business.repository;


import com.example.tddecommerce.domain.order.business.model.ProductOrder;

public interface IProductOrderRepository {

    ProductOrder save(ProductOrder productOrder);


}
