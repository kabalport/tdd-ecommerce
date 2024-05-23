package com.example.tddecommerce.domain.userpoint.business.exception;

public class UserPointBadRequestException extends UserPointException {
    public UserPointBadRequestException(UserPointError error){
        super(error);
    }
}