package com.example.tddecommerce.domain.product.api;

import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        Product product = productService.createProduct(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getDiscountPolicy()
        );
        ProductResponse response = new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getDiscountPolicy(),
                request.getInitialStock()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponse response = new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getDiscountPolicy()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        Product product = productService.updateProduct(
                id,
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getDiscountPolicy()
        );
        ProductResponse response = new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getDiscountPolicy()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
