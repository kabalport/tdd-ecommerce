package com.example.tddecommerce.domain.order.application.facade;

import com.example.tddecommerce.domain.notification.EmailService;
import com.example.tddecommerce.domain.order.api.ProductOrderDetail;
import com.example.tddecommerce.domain.order.api.ProductOrderRequest;
import com.example.tddecommerce.domain.order.api.ProductOrderResponse;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
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
    private ProductStock productStock1;
    private ProductStock productStock2;

    @BeforeEach
    void setUp() {
        // User 생성
        final String name = "choi";
        final String email = "customer@example.com";
        user = userService.addUser(name,email);

        // Product 및 ProductStock 생성
        product1 = new Product("Product 1", BigDecimal.valueOf(100), "Description 1", DiscountPolicy.NONE);
        product2 = new Product("Product 2", BigDecimal.valueOf(200), "Description 2", DiscountPolicy.NONE);

        Product addProduct1 = productService.addProduct(product1.getName(),product1.getPrice(),product1.getDescription(),product1.getDiscountPolicy(),10);
        Product addProduct2 = productService.addProduct(product2.getName(),product2.getPrice(),product2.getDescription(),product2.getDiscountPolicy(),10);


        productStock1 = new ProductStock(addProduct1, 10);
        productStock2 = new ProductStock(addProduct2, 10);

        productStockService.createProductStock(productStock1);
        productStockService.createProductStock(productStock2);

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
                .pointsToUse(BigDecimal.valueOf(50))
                .build();

        // When
        ProductOrderResponse response = productOrderUseCase.execute(request);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getItems().size());
        assertEquals("PAID", response.getStatus());
        assertEquals(BigDecimal.valueOf(750), response.getTotalAmount());

        
    }
}
