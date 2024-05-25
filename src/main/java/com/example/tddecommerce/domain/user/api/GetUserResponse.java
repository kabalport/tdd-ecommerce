package com.example.tddecommerce.domain.user.api;

import lombok.Getter;

@Getter
public class GetUserResponse {
    private final Long userId;
    private final String name;
    private final String email;

    public GetUserResponse(Long userId, String name, String email) {

        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
