package com.example.tddecommerce.domain.product.api;

import com.example.tddecommerce.domain.product.application.service.ProductService;
import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.domain.model.Product;
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
@Transactional
class ProductControllerTest extends IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateProduct() {
        ProductCreateRequest request = new ProductCreateRequest("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE, 10);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Test Product"))
                .body("price", equalTo(100))
                .body("description", equalTo("Description"))
                .body("discountPolicy", equalTo("NONE"))
                .body("initialStock", equalTo(10));
    }

    @Test
    public void testGetProduct() {
        // Create and save a product for testing
        Product product = productService.addProduct("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE, 10);

        given()
                .pathParam("id", product.getId())
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Test Product"))
                .body("price", equalTo(100))
                .body("description", equalTo("Description"))
                .body("discountPolicy", equalTo("NONE"));
    }

    @Test
    public void testUpdateProduct() {
        // Create and save a product for testing
        Product product = productService.addProduct("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE, 10);

        ProductUpdateRequest request = new ProductUpdateRequest("Updated Product", BigDecimal.valueOf(200), "Updated Description", DiscountPolicy.FIX_1000_AMOUNT);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", product.getId())
                .body(request)
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Updated Product"))
                .body("price", equalTo(200))
                .body("description", equalTo("Updated Description"))
                .body("discountPolicy", equalTo("FIX_1000_AMOUNT"));
    }

    @Test
    public void testDeleteProduct() {
        // Create and save a product for testing
        Product product = productService.addProduct("Test Product", BigDecimal.valueOf(100), "Description", DiscountPolicy.NONE, 10);

        given()
                .pathParam("id", product.getId())
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
