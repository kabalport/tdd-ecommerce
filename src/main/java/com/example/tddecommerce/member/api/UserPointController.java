package com.example.tddecommerce.member.api;

import com.example.tddecommerce.member.application.UserPointService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserPointController {
    private final UserPointService userPointService;

    public UserPointController(UserPointService userPointService) {
        this.userPointService = userPointService;
    }


    @PatchMapping("/user/{userId}/charge")
    public void charge(@PathVariable("userId") String userId, @RequestParam(name = "chargingPoint") BigDecimal chargingPoint) {
        userPointService.charge(userId, chargingPoint);
    }
}
