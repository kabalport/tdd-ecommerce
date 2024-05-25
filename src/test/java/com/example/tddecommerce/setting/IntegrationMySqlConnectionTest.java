package com.example.tddecommerce.setting;

import com.example.tddecommerce.setting.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntegrationMySqlConnectionTest extends IntegrationTest {
    @Test
    @DisplayName("통합테스트작동테스트")
    void integrationInitTest() {
        System.out.println("통합테스트의 MySQL 테스트컨테이너가 실행되었습니다.");
    }
}
