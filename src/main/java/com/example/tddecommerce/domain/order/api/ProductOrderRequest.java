package com.example.tddecommerce.domain.order.api;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequest {
    private Long userId;
    private List<ProductOrderDetail> productOrderDetails;
    private BigDecimal pointsToUse;
}
