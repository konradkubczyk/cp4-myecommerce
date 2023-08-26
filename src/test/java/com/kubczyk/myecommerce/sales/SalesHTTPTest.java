package com.kubczyk.myecommerce.sales;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.kubczyk.myecommerce.productcatalog.Product;
import com.kubczyk.myecommerce.sales.reservation.ReservationDetails;
import com.kubczyk.myecommerce.sales.reservation.OfferAcceptanceRequest;
import com.kubczyk.myecommerce.productcatalog.ProductCatalog;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesHTTPTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog productCatalog;

    @Test
    void itAcceptsOffer() {
        
        // Arrange
        String productId = thereIsProduct();
        String uri = getUrl(String.format("/api/cart/%s", productId));

        // Act
        ResponseEntity<Object> resp = http.postForEntity(uri, null, Object.class);
        List<String> cookies = resp.getHeaders().get(HttpHeaders.SET_COOKIE);
        OfferAcceptanceRequest request = new OfferAcceptanceRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        request.setFirstname("Jakub").setLastname("Kanclerz").setEmail("kuba@example.com");
        HttpEntity<OfferAcceptanceRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<ReservationDetails> response = http.postForEntity(getUrl("/api/accept-offer"), httpEntity,
                ReservationDetails.class);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.OK);
        assertNotNull(response.getBody().getPaymentUrl());
        assertNotNull(response.getBody().getReservationId());
    }

    private String thereIsProduct() {
        return productCatalog.allPublishedProducts().stream().findFirst().map(Product::getId).get();
    }

    private String getUrl(String uri) {
        String url = String.format(
                "http://localhost:%s/%s",
                this.port,
                uri);
        return url;
    }
}