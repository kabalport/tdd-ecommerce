package com.example.tddecommerce.order;

import com.example.tddecommerce.order.api.OrderItemDto;
import com.example.tddecommerce.order.business.*;
import com.example.tddecommerce.order.api.CreateOrderRequest;
import com.example.tddecommerce.order.business.domain.Customer;
import com.example.tddecommerce.order.business.domain.Order;
import com.example.tddecommerce.order.business.domain.Product;
import com.example.tddecommerce.order.business.domain.ProductStock;
import com.example.tddecommerce.userPoint.business.UserPoint;
import com.example.tddecommerce.userPoint.infrastructure.IUserPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;

class OrderServiceTest {
    private OrderService orderService;
    private ICustomerRepository customerRepository;
    private IOrderRepository orderRepository;
    private IUserPointRepository userPointRepository;
    private IProductRepository productRepository;
    private IProductStockRepository productStockRepository;
    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        orderRepository = Mockito.mock(IOrderRepository.class);
        userPointRepository = Mockito.mock(IUserPointRepository.class);
        productRepository = Mockito.mock(IProductRepository.class);
        productStockRepository = Mockito.mock(IProductStockRepository.class);
        orderService = new OrderService(customerRepository, orderRepository, userPointRepository, productRepository, productStockRepository);
    }

    public class OrderServiceStep{
        public static CreateOrderRequest createOrderRequest(){
            // 주문요청에 필요한 요소를 세팅합니다.
            final String givenUserId = "userId";
            List<OrderItemDto> orderItemDto = List.of(new OrderItemDto(1L, 3, new BigDecimal(10000)), new OrderItemDto(2L, 32, new BigDecimal(120000)));
            return new CreateOrderRequest(givenUserId, orderItemDto);
        }
    }


    static class OrderFixture {
        static CreateOrderRequest createOrderRequest() {
            final String givenUserId = "userId";
            List<OrderItemDto> orderItemDto = List.of(
                    new OrderItemDto(1L, 3, new BigDecimal(10000)),
                    new OrderItemDto(2L, 32, new BigDecimal(120000))
            );
            return new CreateOrderRequest(givenUserId, orderItemDto);
        }

        static Customer createCustomer() {
            return new Customer(1L, "userId", "customerName", "customerEmail","010-1234-1233");
        }

        static Product createProduct1() {
            return new Product(1L, "Product1", new BigDecimal("10000"), new ProductStock(1L, 1L, null, 10, LocalDateTime.now()));
        }

        static Product createProduct2() {
            return new Product(2L, "Product2", new BigDecimal("120000"), new ProductStock(2L, 2L, null, 40, LocalDateTime.now()));
        }

        static UserPoint createUserPoint() {
            return new UserPoint("userId", new BigDecimal("150000000"));
        }
    }

    @Test
    void createOrder() {
        // given
        CreateOrderRequest request = OrderServiceStep.createOrderRequest();
        when(customerRepository.findByUserId("userId")).thenReturn(Optional.of(new Customer(1L, "userId", "customerName", "customerEmail","010-1234-1233")));
        // Mocking product and stock
        Product product1 = new Product(1L, "Product1", new BigDecimal("10000"), new ProductStock(1L, 1L, null, 10, LocalDateTime.now()));
        Product product2 = new Product(2L, "Product2", new BigDecimal("120000"), new ProductStock(2L, 2L, null, 40, LocalDateTime.now()));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productStockRepository.findByProductId(1L)).thenReturn(Optional.of(product1.getStock()));
        when(productStockRepository.findByProductId(2L)).thenReturn(Optional.of(product2.getStock()));
        when(userPointRepository.findByUserId("userId")).thenReturn(new UserPoint("userId", new BigDecimal("150000000")));

        // when
        // 주문을 생성합니다.
        Order order = orderService.createOrder(request);

        // then
        // 요청한주문이 잘 생성되었는지 검증합니다.
        Assertions.assertEquals(request.getOrderItems().size(), order.getOrderItem().size());
        Assertions.assertEquals("주문완료", order.getOrderStatus());
    }
}
