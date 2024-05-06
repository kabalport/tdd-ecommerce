package com.example.tddecommerce.order;

import com.example.tddecommerce.userPoint.infrastructure.UserPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderServiceTest {
    private OrderService orderService;
    private CustomerRepository customerRepository;

    private OrderRepository orderRepository;

    private UserPointRepository userPointRepository;

    private ProductRepository productRepository;

    private ProductStockRepository productStockRepository;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(this, customerRepository, orderRepository, userPointRepository, productRepository, productStockRepository);
    }
    @Test
    void createOrder() {
        // 주문요청을 생성합니다.
        CreateOrderRequest request = CreateOrderRequestFixture.createOrderRequest();
        // 주문을 생성합니다.
        Order order = orderService.createOrder(request);
        // 요청한주문이 잘 생성되었는지 검증합니다.
        Assertions.assertEquals(request.getCustomerId(), order.getCustomer().getCustomerId());
        Assertions.assertEquals(request.getOrderItems().size(), order.getOrderItem().size());
    }
}
