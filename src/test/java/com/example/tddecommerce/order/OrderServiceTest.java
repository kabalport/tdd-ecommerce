package com.example.tddecommerce.order;

import com.example.tddecommerce.userPoint.business.UserPoint;
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


        // Mock products and stock interactions
        Product product1 = new Product(1L, "Product1", new BigDecimal("10000"));
        Product product2 = new Product(2L, "Product2", new BigDecimal("120000"));
        ProductStock stock1 = new ProductStock(1L, 10);
        ProductStock stock2 = new ProductStock(2L, 40);


        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer(1L, "CustomerName")));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product(1L, "Product1", new BigDecimal("10000"))));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product(2L, "Product2", new BigDecimal("120000"))));


        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productStockRepository.findByProductId(1L)).thenReturn(Optional.of(stock1));
        when(productStockRepository.findByProductId(2L)).thenReturn(Optional.of(stock2));

        // Mock user point interactions
        UserPoint userPoint = new UserPoint("userId", new BigDecimal("150000"));
        when(userPointRepository.findByUserId("userId")).thenReturn(userPoint);

        // when
        // 주문을 생성합니다.
        Order order = orderService.createOrder(request);
        // then
        // 요청한주문이 잘 생성되었는지 검증합니다.
        Assertions.assertEquals(request.getCustomerId(), order.getCustomer().getCustomerId());
        Assertions.assertEquals(request.getOrderItems().size(), order.getOrderItem().size());

        // Verify stock decrease and repository interactions
        Mockito.verify(productStockRepository).save(stock1);
        Mockito.verify(productStockRepository).save(stock2);

        // Verify user points updated
        Mockito.verify(userPointRepository).save(userPoint);
    }
}
