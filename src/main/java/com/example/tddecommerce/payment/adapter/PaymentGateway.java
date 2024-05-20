package com.example.tddecommerce.payment.adapter;

interface PaymentGateway {
    void execute(int totalPrice, String cardNumber);

}
