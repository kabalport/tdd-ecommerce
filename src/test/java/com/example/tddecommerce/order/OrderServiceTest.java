package com.example.tddecommerce.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


class OrderServiceTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void createOrder() {
        // 주문요청을 생성합니다.
        String orderDate = "2021-08-01";
        String orderStatus = "pending";
        BigDecimal orderTotalPrice = new BigDecimal(10000);
        Long customerId = 1L;

        // 주문을 생성합니다.

//        product_id BIGINT NOT NULL,
//        quantity INT NOT NULL,
//        price DECIMAL(10, 2) NOT NULL,

        long productId = 1L;
        int orderQuantity = 3;
        BigDecimal orderPrice = new BigDecimal(10000);
        List<OrderItemDto> orderItemDto = List.of(new OrderItemDto(productId, orderQuantity, orderPrice));
        orderService.createOrder(customerId,orderItemDto);

        // 요청한주문이 잘 생성되었는지 검증합니다.
        Order expectedOrder = new Order(customerId, orderDate, orderStatus, orderTotalPrice);
        Assertions.assertEquals(expectedOrder.getCustomerId(), customerId);

    }



public class OrderService {
        private String orderDate;
        private String orderStatus;
        private BigDecimal orderPrice;
        private Long customerId;


    public void createOrder(Long customerId, List<OrderItemDto> orderItemDto) {
    }
}

    private class Order {
        private final Long customerId;
        private final String orderDate;
        private final String orderStatus;
        private final BigDecimal orderTotalPrice;

        public Long getCustomerId() {
            return customerId;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public BigDecimal getOrderTotalPrice() {
            return orderTotalPrice;
        }

        public Order(Long customerId, String orderDate, String orderStatus, BigDecimal orderTotalPrice) {
            this.customerId = customerId;
            this.orderDate = orderDate;
            this.orderStatus = orderStatus;
            this.orderTotalPrice = orderTotalPrice;


        }
    }

    private class OrderRequest {
    }

    private class OrderItemDto {
        private final long productId;
        private final int orderQuantity;
        private final BigDecimal orderPrice;

        public OrderItemDto(long productId, int orderQuantity, BigDecimal orderPrice) {
            this.productId = productId;
            this.orderQuantity = orderQuantity;
            this.orderPrice = orderPrice;
        }
    }
}
