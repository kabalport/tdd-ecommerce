package com.example.tddecommerce.user.api.dto;

public class UpdateUserNameRequest {
    private String name;

    public UpdateUserNameRequest() {
    }

    public UpdateUserNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
