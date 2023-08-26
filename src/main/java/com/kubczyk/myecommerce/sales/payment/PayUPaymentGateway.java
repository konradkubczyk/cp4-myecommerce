package com.kubczyk.myecommerce.sales.payment;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.kubczyk.myecommerce.payu.Buyer;
import com.kubczyk.myecommerce.payu.OrderCreateRequest;
import com.kubczyk.myecommerce.payu.OrderCreateResponse;
import com.kubczyk.myecommerce.payu.PayU;
import com.kubczyk.myecommerce.payu.Product;

public class PayUPaymentGateway implements PaymentGateway {

    private PayU payU;
    private int requestsCount = 0;

    public PayUPaymentGateway(PayU payU) {
        this.payU = payU;
    }

    @Override
    public PaymentData register(RegisterPaymentRequest request) {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest
                .setNotifyUrl("https://shop.example.com/notify")
                .setCustomerIp("127.0.0.1")
                .setDescription("Traderz")
                .setCurrencyCode("PLN")
                .setTotalAmount(request.getOffer().getTotal().multiply(BigDecimal.valueOf(100)).intValue())
                .setBuyer(new Buyer()
                        .setEmail(request.getClientData().getEmail())
                        .setFirstName(request.getClientData().getFirstname())
                        .setLastName(request.getClientData().getLastname())
                        .setLanguage("pl"))
                .setProducts(request.getOffer().getOrderLines()
                        .stream()
                        .map(ol -> new Product(ol.getName(),
                                ol.getUnitPrice().multiply(BigDecimal.valueOf(100)).intValue(), ol.getQuantity()))
                        .collect(Collectors.toList()));

        OrderCreateResponse response = payU.handle(orderCreateRequest);

        requestsCount++;

        return new PaymentData(response.getOrderId(), response.getRedirectUri());
    }

    public int getRequestsCount() {
        return this.requestsCount;
    }
}
