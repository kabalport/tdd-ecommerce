package com.example.tddecommerce.member.business.exception;

public class UserPointBadRequestException extends UserPointException {
    public UserPointBadRequestException(UserPointError error){
        super(error);
    }
}