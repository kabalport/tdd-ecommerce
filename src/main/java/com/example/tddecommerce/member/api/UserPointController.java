package com.example.ecommercecicd.member.api;

import com.example.ecommercecicd.member.application.UserPointService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class UserPointController {

    private final UserPointService userPointService;


    @PatchMapping("/user/{userId}/charge")
    public void charge(@PathVariable("userId") String userId, @RequestParam(name = "chargingPoint") BigDecimal chargingPoint) {
        userPointService.charge(userId, chargingPoint);
    }
}
