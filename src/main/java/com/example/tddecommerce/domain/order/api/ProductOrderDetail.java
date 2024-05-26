package com.example.tddecommerce.domain.order.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderDetail {

    private Long productId;
    private int quantity;

    public ProductOrderDetail(Long productId, int quantity) {

        this.productId = productId;
        this.quantity = quantity;
    }
}
