package com.example.tddecommerce.domain.productstock.api;

import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.model.Product;
import com.example.tddecommerce.domain.product.infrastructure.ProductRepository;
import com.example.tddecommerce.domain.productstock.business.model.ProductStock;
import com.example.tddecommerce.domain.productstock.business.repository.IProductStockRepository;
import com.example.tddecommerce.setting.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductStockControllerTest extends IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductStockRepository productStockRepository;

    private Product product;
    private ProductStock productStock;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // 상품 생성 및 저장
        product = new Product("Test Product", BigDecimal.valueOf(100), "Test Description", DiscountPolicy.NONE);
        productRepository.save(product);

        // 재고 생성 및 저장
        productStock = new ProductStock(product, 50);
        productStockRepository.save(productStock);
    }

    @Test
    void createProductStock_success() {
//        Product newProduct = new Product("New Product", BigDecimal.valueOf(150), "New Description", DiscountPolicy.NONE);
//        productRepository.save(newProduct);
//
//        ProductStock newProductStock = new ProductStock(newProduct, 30);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(newProductStock)
//                .when()
//                .post("/stock")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body("quantity", equalTo(30));
    }

    @Test
    void getProductStock_success() {
        given()
                .pathParam("productId", product.getId())
                .when()
                .get("/stock/{productId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("quantity", equalTo(50));
    }

    @Test
    void increaseProductStock_success() {
        given()
                .contentType(ContentType.JSON)
                .body(productStock)
                .queryParam("quantity", 20)
                .when()
                .put("/stock/increase")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("quantity", equalTo(70));
    }

    @Test
    void decreaseProductStock_success() {
        given()
                .contentType(ContentType.JSON)
                .body(productStock)
                .queryParam("quantity", 10)
                .when()
                .put("/stock/decrease")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("quantity", equalTo(40));
    }

    @Test
    void decreaseProductStock_insufficientStock_throwsException() {
        given()
                .contentType(ContentType.JSON)
                .body(productStock)
                .queryParam("quantity", 60)
                .when()
                .put("/stock/decrease")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
