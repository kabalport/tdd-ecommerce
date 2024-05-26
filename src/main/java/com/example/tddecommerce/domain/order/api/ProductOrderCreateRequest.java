package com.example.tddecommerce.domain.order.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductOrderCreateRequest {
    private Long userId;
    private List<ProductOrderDetail> products;
}
