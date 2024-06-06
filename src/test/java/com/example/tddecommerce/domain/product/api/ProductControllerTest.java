package com.example.tddecommerce.domain.product.api;

import com.example.tddecommerce.domain.product.application.ProductService;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.setting.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest extends IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    @Transactional
    public void setUp() {
        RestAssured.port = port;
        // 테스트용 상품 생성
        testProduct = productService.createProduct("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE,1000);
        System.out.println("Test Product ID: " + testProduct.getId());
    }

    @Test
    void testCreateProduct() {
        ProductCreateRequest request = new ProductCreateRequest("New Product", BigDecimal.valueOf(200), "New Description", DiscountPolicy.FIX_1000_AMOUNT, 20);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("New Product"))
                .body("price", equalTo(200))
                .body("description", equalTo("New Description"))
                .body("discountPolicy", equalTo("FIX_1000_AMOUNT"))
                .body("initialStock", equalTo(20));
    }

    @Test
    void testGetProduct() {
        given()
                .pathParam("id", testProduct.getId())
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Test Product"))
                .body("price", equalTo(100.0F))
                .body("description", equalTo("Description"))
                .body("discountPolicy", equalTo("NONE"));
    }

    @Test
    void testUpdateProduct() {
        ProductUpdateRequest request = new ProductUpdateRequest("Updated Product", BigDecimal.valueOf(150), "Updated Description", DiscountPolicy.FIX_1000_AMOUNT);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", testProduct.getId())
                .body(request)
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Updated Product"))
                .body("price", equalTo(150))
                .body("description", equalTo("Updated Description"))
                .body("discountPolicy", equalTo("FIX_1000_AMOUNT"));
    }

    @Test
    void testDeleteProduct() {
        given()
                .pathParam("id", testProduct.getId())
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Verify product deletion
//        given()
//                .pathParam("id", testProduct.getId())
//                .when()
//                .get("/products/{id}")
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value());

    }
}
