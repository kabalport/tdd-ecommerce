package com.example.tddecommerce.domain.order.api;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetail {
    private Long productId;
    private int quantity;
}

