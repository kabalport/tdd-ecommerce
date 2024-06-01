package com.example.tddecommerce.domain.productstock.business.model;

import com.example.tddecommerce.domain.product.business.exception.ProductException;
import com.example.tddecommerce.domain.product.business.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ecommerce_product_stock")
@Getter
@NoArgsConstructor
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_stock_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private int quantity;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public ProductStock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public void decrease(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new ProductException("재고는 0개 미만이 될 수 없습니다.");
        }
        this.quantity -= quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public void increase(int quantity) {
        if (quantity > 1000) { // 비즈니스 로직에 따라 이 조건을 수정할 수 있습니다.
            throw new ProductException("재고는 1000개 초과가 될 수 없습니다.");
        }
        this.quantity += quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
