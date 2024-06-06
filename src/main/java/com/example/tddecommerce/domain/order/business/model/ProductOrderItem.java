package com.example.tddecommerce.domain.order.business.model;

import com.example.tddecommerce.domain.product.business.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private ProductOrder productOrder;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    public ProductOrderItem(Long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }


    void setProductOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }
}
