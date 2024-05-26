package com.example.tddecommerce.domain.order.business.component;

import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductOrderValidate {


    public void validate(Long userId, List<ProductOrderDetail> productOrderDetails) {

    }
}
