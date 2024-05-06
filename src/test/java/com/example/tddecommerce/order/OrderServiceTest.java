package com.example.tddecommerce.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class OrderServiceTest {

    @Test
    void createOrder() {
        // 주문을 생성합니다.
        String orderDate = "2021-08-01";
        String orderStatus = "pending";
        BigDecimal orderPrice = new BigDecimal(10000);
        Long customerId = 1L;


        // 요청한주문이 잘 생성되었는지 검증합니다.
        Order expectedOrder = new Order(customerId, orderDate, orderStatus, orderPrice);
        Assertions.assertEquals(expectedOrder.getCustomerId(), customerId);

    }



public class OrderService {
        private String orderDate;
        private String orderStatus;
        private BigDecimal orderPrice;
        private Long customerId;

        public Order createOrder(String orderDate, BigDecimal orderPrice, Long customerId) {
            this.orderDate = orderDate;
            this.orderPrice = orderPrice;
            this.customerId = customerId;



            return null;
        }
    }

    private class Order {
        private final Long customerId;
        private final String orderDate;
        private final String orderStatus;
        private final BigDecimal orderPrice;

        public Long getCustomerId() {
            return customerId;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public BigDecimal getOrderPrice() {
            return orderPrice;
        }

        public Order(Long customerId, String orderDate, String orderStatus, BigDecimal orderPrice) {
            this.customerId = customerId;
            this.orderDate = orderDate;
            this.orderStatus = orderStatus;
            this.orderPrice = orderPrice;


        }
    }
}
