package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
import com.example.tddecommerce.domain.order.api.ProductOrderResponse;
import com.example.tddecommerce.domain.payment.business.PaymentService;
import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ProductOrderUseCaseTest extends IntegrationTest {

    @Autowired
    private ProductOrderUseCase productOrderUseCase;
    @Autowired
    private UserService userService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    private User user;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        // User 생성
        final String name = "choi";
        final String email = "customer@example.com";
        user = userService.addUser(name,email);

        // Product 및 ProductStock 생성
        final String productName1 = "Product 1";
        final BigDecimal productPrice1 = BigDecimal.valueOf(100);
        final String productDescription1 = "Description 1";
        final DiscountPolicy productDiscountPolicy1 = DiscountPolicy.NONE;
        final int initialProductStock1 = 1000;
        final String productName2 = "Product 2";
        final BigDecimal productPrice2 = BigDecimal.valueOf(200);
        final String productDescription2 = "Description 2";
        final DiscountPolicy productDiscountPolicy2 = DiscountPolicy.NONE;
        final int initialProductStock2 = 1000;

        product1 = productService.createProduct(productName1,productPrice1,productDescription1,productDiscountPolicy1,initialProductStock1);
        product2 = productService.createProduct(productName2,productPrice2,productDescription2,productDiscountPolicy2,initialProductStock2);
    }

    @Test
    void testExecute() {
        // Given
        ProductOrderDetail detail1 = new ProductOrderDetail(product1.getId(), 2);
        ProductOrderDetail detail2 = new ProductOrderDetail(product2.getId(), 3);
        List<ProductOrderDetail> productOrderDetails = Arrays.asList(detail1, detail2);
        ProductOrderRequest request = ProductOrderRequest.builder()
                .userId(user.getUserId())
                .productOrderDetails(productOrderDetails)
                .build();

        // When
        ProductOrderResult response = productOrderUseCase.execute(request);

        // Then
        assertNotNull(response);


    }
}
