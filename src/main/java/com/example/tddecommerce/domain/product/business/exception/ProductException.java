package com.example.tddecommerce.domain.product.business.exception;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Exception exception) {
        super(message,exception);
    }
}
