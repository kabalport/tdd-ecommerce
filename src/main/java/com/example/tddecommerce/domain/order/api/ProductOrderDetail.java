package com.example.tddecommerce.domain.order.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetail {

    private Long productId;
    private int quantity;
}
