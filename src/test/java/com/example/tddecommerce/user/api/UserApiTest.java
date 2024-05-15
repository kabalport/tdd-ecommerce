package com.example.tddecommerce.user.api;

import com.example.tddecommerce.IntegrationTest;
import com.example.tddecommerce.user.api.dto.CreateUserRequest;
import com.example.tddecommerce.user.api.dto.UpdateUserNameRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiTest extends IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void 유저생성() throws Exception {
        // given
        CreateUserRequest request = UserFixture.createUserRequest();

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(request))
                .when()
                .post("/api/user")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body("name", equalTo(request.getName()))
                .body("email", equalTo(request.getEmail()))
                .log().all().extract();
    }

    @Test
    @DisplayName("유저를 조회한다.")
    void 유저조회() throws Exception {
        // given
        RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .body(objectMapper.writeValueAsString(UserFixture.createUserRequest()))
                        .when()
                        .post("/api/user")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(ContentType.JSON)
                        .log().all().extract();

        Long userId = 1L;


        final ExtractableResponse<Response> response =
                RestAssured.given().log().all()
                        .accept(ContentType.JSON)  // Accept 헤더 추가
                        .contentType(ContentType.JSON)
                .when()
                .get("/api/user/{userId}",userId)
                .then().log().all()
                .extract();
        Assertions.assertEquals(response.statusCode(),HttpStatus.OK.value());
    }

    @Test
    @DisplayName("유저의 이름을 업데이트한다.")
    void 유저이름수정() throws Exception {
        // given
        CreateUserRequest createRequest = UserFixture.createUserRequest();

        // 유저를 생성하고 그 ID를 가져옴
        Long userId = ((Integer) given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(createRequest))
                .when()
                .post("/api/user")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .path("userId")).longValue();

        // 새로운 이름 요청 생성
        UpdateUserNameRequest updateRequest = new UpdateUserNameRequest("김길동");

        // when
        given().log().all()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(updateRequest))
                .when()
                .put("/api/user/{userId}/name", userId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all().extract();

        // then
        given().log().all()
                .accept(ContentType.JSON)
                .when()
                .get("/api/user/{userId}", userId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("name", equalTo("김길동"))
                .body("email", equalTo(createRequest.getEmail()))
                .log().all().extract();
    }

    private static class UserFixture {
        public static CreateUserRequest createUserRequest() {
            return new CreateUserRequest("홍길동", "gildong@gmail.com");
        }
    }
}
