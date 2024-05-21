package com.example.tddecommerce.product.application.service;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Exception exception) {
        super(message,exception);
    }
}
