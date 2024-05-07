package com.example.tddecommerce.order.business;

import com.example.tddecommerce.order.business.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findById(long customerId);

    Optional<Customer> findByUserId(String userId);

}
