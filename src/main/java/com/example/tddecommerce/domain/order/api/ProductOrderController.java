package com.example.tddecommerce.domain.order.api;

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

    /**
     * 제품 주문을 처리한다
     * @param productOrderRequest 주문 요청 객체
     * @return 주문 결과
     */
    @PostMapping
    public ResponseEntity<ProductOrderResponse> processProductOrder(@RequestBody ProductOrderRequest productOrderRequest) {
        try {
            ProductOrderResponse response = productOrderUseCase.execute(productOrderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

