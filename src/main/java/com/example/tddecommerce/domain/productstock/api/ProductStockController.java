package com.example.tddecommerce.domain.productstock.api;

import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class ProductStockController {
    private final ProductService productService;
    private final ProductStockService productStockService;


    /**
     * 특정 상품의 재고를 조회합니다.
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 재고 객체
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductStock> getProductStock(@PathVariable Long productId) {
        // 상품을 ID를 사용하여 조회
        Product product = productService.getProductById(productId);

        ProductStock productStock = productStockService.getProductStock(product.getId());
        return ResponseEntity.ok(productStock);
    }

    /**
     * 특정 상품 재고를 증가시킵니다.
     * @param productStock 상품 재고 객체
     * @param quantity 증가시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    @PutMapping("/increase")
    public ResponseEntity<ProductStock> increaseProductStock(@RequestBody ProductStock productStock, @RequestParam int quantity) {
        ProductStock updatedProductStock = productStockService.increaseProductStock(productStock, quantity);
        return ResponseEntity.ok(updatedProductStock);
    }

    /**
     * 특정 상품 재고를 감소시킵니다.
     * @param productStock 상품 재고 객체
     * @param quantity 감소시킬 수량
     * @return 업데이트된 상품 재고 객체
     */
    @PutMapping("/decrease")
    public ResponseEntity<ProductStock> decreaseProductStock(@RequestBody ProductStock productStock, @RequestParam int quantity) {
        ProductStock updatedProductStock = productStockService.decreaseProductStock(productStock, quantity);
        return ResponseEntity.ok(updatedProductStock);
    }
}
