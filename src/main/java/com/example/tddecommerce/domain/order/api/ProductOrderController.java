package com.example.tddecommerce.domain.order.api;

import com.example.tddecommerce.domain.order.application.facade.ProductOrderResult;
import com.example.tddecommerce.domain.order.application.facade.ProductOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderUseCase productOrderUseCase;

    @PostMapping
    public ResponseEntity<ProductOrderResponse> createOrder(@RequestBody ProductOrderRequest productOrderRequest) {
        try {
            ProductOrderResult productOrderResult = productOrderUseCase.execute(productOrderRequest);
            ProductOrderResponse response = convertToResponse(productOrderResult);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private ProductOrderResponse convertToResponse(ProductOrderResult productOrderResult) {
        return ProductOrderResponse.builder()
                .orderId(productOrderResult.getOrder().getId())
                .status(productOrderResult.getOrder().getOrderStatus().toString())
                .items(productOrderResult.getOrder().getOrderItems().stream()
                        .map(item -> ProductOrderResponse.OrderItem.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .toList())
                .totalAmount(productOrderResult.getOrder().getTotalPrice())
                .build();
    }
}
