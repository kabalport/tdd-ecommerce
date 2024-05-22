package com.example.tddecommerce.order.business.model;

import com.example.tddecommerce.user.business.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ecommerce_order")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User member;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "productOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductOrderItem> items;

    @Enumerated(EnumType.STRING)
    private ProductOrderStatus status;



    public static ProductOrder createProductOrder(User member, LocalDate orderDate, ProductOrderStatus status, List<ProductOrderItem> items) {
        ProductOrder order = new ProductOrder();
        order.member = member;
        order.orderDate = orderDate;
        order.status = status;
        order.items = new ArrayList<>();
        if (items != null) {
            for (ProductOrderItem item : items) {
                order.addProductOrderItem(item);
            }
        }
        return order;
    }

    // 연결 메소드는 그대로 유지
    public void addProductOrderItem(ProductOrderItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        item.setProductOrder(this);
    }

    // 주문 상태를 설정하는 메서드
    public void setStatus(ProductOrderStatus status) {
        this.status = status;
    }
}
