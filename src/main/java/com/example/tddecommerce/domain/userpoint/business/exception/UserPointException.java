package com.example.tddecommerce.domain.userpoint.business.exception;

import lombok.Getter;

@Getter
public class UserPointException extends RuntimeException {

    private final UserPointError error;

    public UserPointException(UserPointError error){
        this.error = error;
    }


}
