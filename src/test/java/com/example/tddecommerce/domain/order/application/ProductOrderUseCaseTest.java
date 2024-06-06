package com.example.tddecommerce.domain.order.application;

import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.productstock.application.ProductStockService;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
import com.example.tddecommerce.domain.userpoint.business.model.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ProductOrderUseCaseTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private ProductStockService productStockService;




    private User user;

    @BeforeEach
    void setUp() {
        // 상품 만들기
        Product addProduct = productService.createProduct("New Product", BigDecimal.valueOf(200), "New Description", DiscountPolicy.FIX_1000_AMOUNT,1000);
        // 상품 재고설정하기
        ProductStock addProductStock = new ProductStock(addProduct.getId(), 50);
//        productStockService.createProductStock(addProductStock);
        // 유저 만들기
        final String name = "홍길동";
        final String email = "gildong@gmail.com";
        User addUser = userService.addUser(name,email);
        // 유저 포인트 충전하기
        UserPoint addUserPoint = userPointService.chargeUserPoint(user.getUserId(), BigDecimal.valueOf(100000L));


    }
}
