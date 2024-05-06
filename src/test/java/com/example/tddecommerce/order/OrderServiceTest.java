package com.example.tddecommerce.order;

import com.example.tddecommerce.userPoint.infrastructure.IUserPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class OrderServiceTest {
    private OrderService orderService;
    private CustomerRepository customerRepository;

    private OrderRepository orderRepository;

    private IUserPointRepository userPointRepository;

    private ProductRepository productRepository;

    private ProductStockRepository productStockRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository();
        orderRepository = new OrderRepository();
        userPointRepository = Mockito.mock(IUserPointRepository.class);
        productRepository = new ProductRepository();
        productStockRepository = new ProductStockRepository();
        orderService = new OrderService(customerRepository, orderRepository, userPointRepository, productRepository, productStockRepository);
    }

    public class OrderServiceStep{
        public static CreateOrderRequest createOrderRequest(){
            // 주문요청에 필요한 요소를 세팅합니다.
            final String givenUserId = "userId";
            Long givenCustomerId = 1L;
            List<OrderItemDto> orderItemDto = List.of(new OrderItemDto(1L, 3, new BigDecimal(10000)), new OrderItemDto(2L, 32, new BigDecimal(120000)));
            return new CreateOrderRequest(givenUserId,givenCustomerId, orderItemDto);
        }
    }

    @Test
    void createOrder() {
        // given
        // 주문요청을 생성합니다.
        CreateOrderRequest request = OrderServiceStep.createOrderRequest();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer(1L, "CustomerName")));
        //when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product(1L, "Product1", new BigDecimal("10000"))));
        //when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product(2L, "Product2", new BigDecimal("120000"))));

        // when
        // 주문을 생성합니다.
        Order order = orderService.createOrder(request);
        // then
        // 요청한주문이 잘 생성되었는지 검증합니다.
        Assertions.assertEquals(request.getCustomerId(), order.getCustomer().getCustomerId());
        Assertions.assertEquals(request.getOrderItems().size(), order.getOrderItem().size());
    }
}
