package com.example.tddecommerce.domain.product.business.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a product in the e-commerce platform.
 */
@Entity
@Table(name = "ecommerce_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    // 상품아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    // 상품명
    @Column(name = "name", nullable = false)
    private String name;

    // 가격
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // 상품 설명
    @Column(name = "description", length = 1000)
    private String description;

    // 할인정책
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_policy", nullable = false)
    private DiscountPolicy discountPolicy;

    // 삭제유무
    @Column(name = "del_flag", nullable = false)
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
