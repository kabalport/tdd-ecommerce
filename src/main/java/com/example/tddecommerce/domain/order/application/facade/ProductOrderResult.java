package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import lombok.Getter;

@Getter
public class ProductOrderResult {
    private final ProductOrder order;
    private final Payment payment;

    public ProductOrderResult(ProductOrder order, Payment payment) {
        this.order = order;
        this.payment = payment;
    }
}
