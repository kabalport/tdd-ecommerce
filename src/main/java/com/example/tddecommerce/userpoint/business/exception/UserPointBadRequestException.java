package com.example.tddecommerce.userpoint.business.exception;

public class UserPointBadRequestException extends UserPointException {
    public UserPointBadRequestException(UserPointError error){
        super(error);
    }
}