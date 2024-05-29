package com.example.tddecommerce.domain.order.business.model;

import com.example.tddecommerce.domain.product.business.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ecommerce_product_order")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private ProductOrderStatus status;

    public void setStatus(ProductOrderStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL)
    private List<ProductOrderItem> items;

    private BigDecimal totalAmount;
    private BigDecimal amountToBePaid;
}
