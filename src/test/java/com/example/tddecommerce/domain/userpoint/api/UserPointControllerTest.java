package com.example.tddecommerce.domain.userpoint.api;

import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.domain.user.business.domain.User;
import com.example.tddecommerce.domain.userpoint.application.UserPointService;
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
class UserPointControllerTest extends IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserPointService userPointService;

    @Autowired
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        // 테스트용 사용자 생성
        testUser = userService.registerUser("Test User","test@example.com");
    }
    @Test
    public void testChargeUserPoint() {
        BigDecimal chargeAmount = new BigDecimal("50.00");

        given()
                .contentType(ContentType.JSON)
                .queryParam("userId", testUser.getUserId())
                .queryParam("chargeAmount", chargeAmount)
                .when()
                .post("/api/userpoints/charge")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("pointBalance", equalTo(50.0F)); // Assuming initial balance is 0
    }



    @Test
    public void testGetUserPoint() {
        BigDecimal initialChargeAmount = new BigDecimal("50.00");

        // Pre-charge the user points for the test
        userPointService.chargeUserPoint(testUser.getUserId(), initialChargeAmount);

        System.out.println(testUser.getUserId());
        System.out.println(testUser.getUserId());
        System.out.println(testUser.getUserId());
        System.out.println(initialChargeAmount);

        given()
                .pathParam("userId", testUser.getUserId())
                .when()
                .get("/api/userpoints/{userId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("pointBalance", equalTo(50.00F)); // Adjust according to the initial charge
    }
}
