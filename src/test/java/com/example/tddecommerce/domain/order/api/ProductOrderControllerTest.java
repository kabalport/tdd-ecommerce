package com.example.tddecommerce.domain.order.api;

import com.example.tddecommerce.domain.order.application.facade.ProductOrderResult;
import com.example.tddecommerce.domain.order.application.facade.ProductOrderUseCase;
import com.example.tddecommerce.domain.order.business.model.ProductOrder;
import com.example.tddecommerce.domain.order.business.model.ProductOrderItem;
import com.example.tddecommerce.domain.order.business.model.ProductOrderStatus;
import com.example.tddecommerce.domain.payment.business.model.Payment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductOrderControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private ProductOrderUseCase productOrderUseCase;

    @Autowired
    private ProductOrderController productOrderController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createOrder_success() {
        // Given
        ProductOrderRequest request = ProductOrderRequest.builder()
                .userId(1L)
                .productOrderDetails(List.of(
                        ProductOrderDetail.builder().productId(1L).quantity(2).build(),
                        ProductOrderDetail.builder().productId(2L).quantity(1).build()
                ))
                .build();

        ProductOrder order = ProductOrder.builder()
                .id(1L)
                .userId(1L)
                .orderStatus(ProductOrderStatus.PAID)
                .orderItems(List.of(
                        ProductOrderItem.builder().productId(1L).quantity(2).price(new BigDecimal("20.00")).build(),
                        ProductOrderItem.builder().productId(2L).quantity(1).price(new BigDecimal("10.00")).build()
                ))
                .totalPrice(new BigDecimal("30.00"))
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .amount(new BigDecimal("30.00"))
                .build();

        ProductOrderResult result = new ProductOrderResult(order, payment);

        Mockito.when(productOrderUseCase.execute(Mockito.any(ProductOrderRequest.class)))
                .thenReturn(result);

        // When / Then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("orderId", equalTo(1))
                .body("status", equalTo("PAID"))
                .body("items.size()", equalTo(2))
                .body("totalAmount", equalTo(30.00f))
                .body("items[0].productId", equalTo(1))
                .body("items[0].quantity", equalTo(2))
                .body("items[0].price", equalTo(20.00f))
                .body("items[1].productId", equalTo(2))
                .body("items[1].quantity", equalTo(1))
                .body("items[1].price", equalTo(10.00f));
    }

    @Test
    void createOrder_failure() {
        // Given
        ProductOrderRequest request = ProductOrderRequest.builder()
                .userId(1L)
                .productOrderDetails(List.of(
                        ProductOrderDetail.builder().productId(1L).quantity(2).build(),
                        ProductOrderDetail.builder().productId(2L).quantity(1).build()
                ))
                .build();

        Mockito.when(productOrderUseCase.execute(Mockito.any(ProductOrderRequest.class)))
                .thenThrow(new RuntimeException("Order processing failed"));

        // When / Then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
