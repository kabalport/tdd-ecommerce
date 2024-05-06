package com.example.tddecommerce.userPoint.business.exception;

public class UserPointBadRequestException extends UserPointException {
    public UserPointBadRequestException(UserPointError error){
        super(error);
    }
}