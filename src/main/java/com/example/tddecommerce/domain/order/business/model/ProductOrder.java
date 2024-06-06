package com.example.tddecommerce.domain.order.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="`order`")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private ProductOrderStatus orderStatus;

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOrderItem> orderItems = new ArrayList<>();

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    public ProductOrder(Long userId, LocalDate orderDate, ProductOrderStatus orderStatus, List<ProductOrderItem> orderItems) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public void setOrderStatus(ProductOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderItems(List<ProductOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(ProductOrderItem item) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(item);
        item.setProductOrder(this);
    }
}

