package com.kubczyk.myecommerce.payu;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PayUTest {

    @Test
    void itRegistersPayment() {
        PayU payu = thereIsPayU();
        OrderCreateRequest request = thereIsExampleOrderCreateRequest();

        OrderCreateResponse response = payu.handle(request);

        assertNotNull(response.getRedirectUri());
        assertNotNull(response.getOrderId());
    }

    private PayU thereIsPayU() {
        return new PayU(new RestTemplate());
    }

    private OrderCreateRequest thereIsExampleOrderCreateRequest() {
        OrderCreateRequest exampleRequest = new OrderCreateRequest();
        exampleRequest
                .setNotifyUrl("https://your.eshop.com/notify")
                .setCustomerIp("127.0.0.1")
                .setMerchantPosId("300746")
                .setDescription("RTV market")
                .setCurrencyCode("PLN")
                .setTotalAmount(21000)
                .setBuyer(new Buyer()
                        .setEmail("john.doe@example.com")
                        .setPhone("654111654")
                        .setFirstName("John")
                        .setLanguage("Doe")
                        .setLanguage("pl")
                )
                .setProducts(Collections.singletonList(
                        new Product()
                                .setName("Product name")
                                .setQuantity(1)
                                .setUnitPrice(21000)
                ));
        return exampleRequest;
    }
}
