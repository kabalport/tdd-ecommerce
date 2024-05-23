package com.example.tddecommerce.api.userpoint;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserPointRequest {

    private final String userId;

    private final BigDecimal addPoint;


    public UserPointRequest(String userId, BigDecimal addPoint) {
        this.userId = userId;
        this.addPoint = addPoint;
    }
}
