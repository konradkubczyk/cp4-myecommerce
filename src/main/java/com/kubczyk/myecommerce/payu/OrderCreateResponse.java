package com.kubczyk.myecommerce.payu;

public class OrderCreateResponse {

    String redirectUri;
    String orderId;

    public String getRedirectUri() {
        return redirectUri;
    }

    public OrderCreateResponse setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderCreateResponse setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
