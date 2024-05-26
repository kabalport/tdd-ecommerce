package com.example.tddecommerce.domain.order.api;

import com.example.tddecommerce.domain.order.application.ProductOrderAndPayUseCase;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
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
    public ProductOrderCreateResponse order(@RequestBody ProductOrderCreateRequest request) {
        ProductOrder productOrder = productOrderAndPayUseCase.execute(request.getUserId(), request.getProducts());

        return entityToResponse(productOrder);
    }

    private ProductOrderCreateResponse entityToResponse(ProductOrder productOrder) {
        List<ProductOrderCreateResponse.OrderItem> items = productOrder.getItems().stream()
                .map(item -> ProductOrderCreateResponse.OrderItem.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return ProductOrderCreateResponse.builder()
                .orderId(productOrder.getId())
                .status(productOrder.getStatus().name())
                .items(items)
                .totalAmount(calculateTotalAmount(items))
                .build();
    }

    private BigDecimal calculateTotalAmount(List<ProductOrderCreateResponse.OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
