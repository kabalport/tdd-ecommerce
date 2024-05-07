package com.example.tddecommerce.order.business.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long orderId;

    private Long customerId;

    private Customer customer;
    private String orderDate;
    private String orderStatus;
    private BigDecimal orderTotalPrice;

    private List<OrderItem> orderItem;

    public Order() {

    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Customer getCustomer() {
        return customer;
    }

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


    public List<OrderItem> getOrderItem() {
        return orderItem;
    }
}
