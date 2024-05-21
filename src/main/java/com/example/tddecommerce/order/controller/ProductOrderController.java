package com.example.tddecommerce.order.controller;

import com.example.tddecommerce.order.business.model.ProductOrder;
import com.example.tddecommerce.order.usecase.ProductOrderAndPayUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/order")
public class ProductOrderController {
    private final ProductOrderAndPayUseCase productOrderAndPayUseCase;

    @PostMapping
    public ProductOrderDTO.Response order(@RequestBody ProductOrderDTO.Request request) {
        ProductOrder response = productOrderAndPayUseCase.execute(request);

        return entityToDTO(response);
    }

    private ProductOrderDTO.Response entityToDTO(ProductOrder productOrder) {
        List<ProductOrderDTO.OrderItem> items = productOrder.getItems().stream()
                .map(item -> ProductOrderDTO.OrderItem.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return ProductOrderDTO.Response.builder()
                .orderId(productOrder.getId())
                .status(productOrder.getStatus().name())
                .items(items)
                .totalAmount(calculateTotalAmount(items))
                .build();
    }

    private BigDecimal calculateTotalAmount(List<ProductOrderDTO.OrderItem> items) {
        return null;
//        return items.stream()
//                .mapToInt(item -> item.getPrice() * item.getQuantity())
//                .sum();
    }
}
