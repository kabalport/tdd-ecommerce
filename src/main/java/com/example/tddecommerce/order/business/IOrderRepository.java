package com.example.tddecommerce.order.business;

import com.example.tddecommerce.order.business.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {


}
