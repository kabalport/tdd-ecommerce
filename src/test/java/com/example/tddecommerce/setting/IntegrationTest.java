package com.example.tddecommerce.setting;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.io.File;


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
public class IntegrationTest {
  // Docker Compose 파일을 사용하는 DockerComposeContainer 인스턴스 생성
  public static DockerComposeContainer<?> environment =
          new DockerComposeContainer<>(new File("infra/test/docker-compose.yaml"))
                  .withExposedService("mysql", 3306);

  static {
    // 컨테이너 시작
    Startables.deepStart(environment).join();
  }

  // 테스트를 위한 동적 프로퍼티 설정
  @DynamicPropertySource
  static void mysqlProperties(DynamicPropertyRegistry registry) {
    String jdbcUrl =
            String.format(
                    "jdbc:mysql://localhost:%d/integration_tests_db",
                    environment.getServicePort("mysql", 3306));
    registry.add("spring.datasource.url", () -> jdbcUrl);
    registry.add("spring.datasource.username", () -> "sa");
    registry.add("spring.datasource.password", () -> "sa");
    registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
  }
}
