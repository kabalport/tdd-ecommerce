package com.example.tddecommerce.api.user.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserRequest {
    private String name;
    private String email;


    public CreateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;

    }


}