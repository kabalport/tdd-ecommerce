package com.example.tddecommerce.order.repository;

import com.example.tddecommerce.order.business.domain.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepository {

    private Map<Long, Customer> persistence = new HashMap<>();

    private Long sequence = 0L;

    public Optional<Customer> findById(Long customerId) {
        return Optional.ofNullable(persistence.get(customerId));
    }

    public Customer save(Customer customer) {
        if (customer.getCustomerId() == null) {
            customer.setCustomerId(++sequence);
        }
        persistence.put(customer.getCustomerId(), customer);
        return customer;
    }
}
