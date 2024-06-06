package com.example.tddecommerce.domain.payment.infrastructure;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

//    @Query("SELECT p FROM Payment p WHERE p. order = :order")
//    Optional<Payment> findByOrder(@Param("order") ProductOrder order);
}
