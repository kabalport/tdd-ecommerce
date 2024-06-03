package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.order.business.repository.IProductOrderRepository;
import com.example.tddecommerce.domain.user.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductOrderReaderTest {

    private IProductOrderRepository productOrderRepository;
    private ProductOrderReader productOrderReader;

    ProductOrderItem orderItem;

    @BeforeEach
    void setUp() {
        productOrderRepository = Mockito.mock(IProductOrderRepository.class);
        productOrderReader = new ProductOrderReader(productOrderRepository);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        User user = new User("홍길동", "gildong@gmail.com");

        // Create a ProductOrder instance
        ProductOrder order = new ProductOrder(user.getUserId(), LocalDate.now(), ProductOrderStatus.PENDING, Collections.singletonList(orderItem));

        // Mock the repository findById method
        when(productOrderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Call getOrderById
        Optional<ProductOrder> foundOrder = productOrderReader.getOrderById(orderId);

        // Verify the findById method was called
        verify(productOrderRepository).findById(orderId);
        assertTrue(foundOrder.isPresent());
        assertEquals(order, foundOrder.get());
    }

    @Test
    void testGetOrdersByUser() {
        User user = new User("홍길동", "gildong@gmail.com");

        // Create a list of ProductOrder instances
        ProductOrder order = new ProductOrder(user.getUserId(), LocalDate.now(), ProductOrderStatus.PENDING, Collections.singletonList(orderItem));
        List<ProductOrder> orders = Collections.singletonList(order);

        // Mock the repository findByUser method
        when(productOrderRepository.findByUser(user)).thenReturn(orders);

        // Call getOrdersByUser
        List<ProductOrder> foundOrders = productOrderReader.getOrdersByUser(user);

        // Verify the findByUser method was called
        verify(productOrderRepository).findByUser(user);
        assertEquals(orders, foundOrders);
    }

    @Test
    void testGetOrdersByStatus() {
        User user = new User("홍길동", "gildong@gmail.com");
        ProductOrderStatus status = ProductOrderStatus.PENDING;

        // Create a list of ProductOrder instances
        ProductOrder order = new ProductOrder(user.getUserId(), LocalDate.now(), ProductOrderStatus.PENDING, Collections.singletonList(orderItem));
        List<ProductOrder> orders = Collections.singletonList(order);

        // Mock the repository findByOrderStatus method
        when(productOrderRepository.findByOrderStatus(status)).thenReturn(orders);

        // Call getOrdersByStatus
        List<ProductOrder> foundOrders = productOrderReader.getOrdersByStatus(status);

        // Verify the findByOrderStatus method was called
        verify(productOrderRepository).findByOrderStatus(status);
        assertEquals(orders, foundOrders);
    }
}
