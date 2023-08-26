package com.kubczyk.myecommerce.sales.payment;

public interface PaymentGateway {
    PaymentData register(RegisterPaymentRequest request);
}
