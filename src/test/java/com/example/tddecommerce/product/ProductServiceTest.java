package com.example.tddecommerce.product;

import org.junit.jupiter.api.Test;

public class ProductServiceTest {

    @Test
    void 상품등록(){
        final AddProductRequest request = new AddProductRequest("상품명",1000,DisCountPolicy.None);
        productService.addProduct(request);
    }

    private class AddProductRequest {
    }
}
