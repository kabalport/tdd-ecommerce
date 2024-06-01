package com.example.tddecommerce.domain.product.business.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ecommerce_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_policy", nullable = false)
    private DiscountPolicy discountPolicy;

    @Column(name = "del_flag", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean delFlag;

    public Product(String name, BigDecimal price, String description, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPolicy = discountPolicy;
        this.delFlag = false;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void update(String name, BigDecimal price, String description, DiscountPolicy discountPolicy) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPolicy = discountPolicy;
    }

    public BigDecimal getDiscountedPrice() {
        return discountPolicy.applyDiscount(price);
    }
}
