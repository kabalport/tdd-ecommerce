package com.example.tddecommerce.domain.order.infrastructure;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductOrderJpaRepository extends JpaRepository<ProductOrder, Long> {



}
