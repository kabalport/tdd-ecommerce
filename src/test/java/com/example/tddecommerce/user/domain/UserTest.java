package com.example.tddecommerce.user.domain;

import com.example.tddecommerce.user.business.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserTest {

    @Test
    void updateName() {
        //given
        final User user = new User("길동","gildong@gmail.com", LocalDateTime.now(),LocalDateTime.now());
        final String nameUpdate = "철수";
        // when
        user.updateName(nameUpdate);

        // then
        Assertions.assertEquals(nameUpdate,user.getName());
    }
}
