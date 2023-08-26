package com.kubczyk.myecommerce.payu;

public class PayUException extends Exception {
    public PayUException(Exception e) {
        super(e);
    }
}
