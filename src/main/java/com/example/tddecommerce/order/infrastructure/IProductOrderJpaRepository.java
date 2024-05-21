package com.example.tddecommerce.order.infrastructure;

import com.example.tddecommerce.order.business.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductOrderJpaRepository extends JpaRepository<ProductOrder, Long> {



}
