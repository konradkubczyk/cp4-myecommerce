package com.kubczyk.myecommerce.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.payment.SpyPaymentGateway;
import com.kubczyk.myecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import com.kubczyk.myecommerce.sales.productdetails.ProductDetails;
import com.kubczyk.myecommerce.sales.reservation.InMemoryReservationStorage;
import com.kubczyk.myecommerce.sales.reservation.OfferAcceptanceRequest;
import com.kubczyk.myecommerce.sales.reservation.Reservation;
import com.kubczyk.myecommerce.sales.reservation.ReservationDetails;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class OfferAcceptanceTest {

    private CartStorage cartStorage;
    private SpyPaymentGateway paymentGateway;
    private InMemoryReservationStorage reservationStorage;
    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setUp() {
        this.cartStorage = new CartStorage();
        this.paymentGateway = new SpyPaymentGateway();
        this.reservationStorage = new InMemoryReservationStorage();
        this.productDetails = new InMemoryProductDetailsProvider();
    }

    private void assertReservationIsPending(String reservationId) {
        // Arrange & Act
        Reservation reservation = reservationStorage.load(reservationId).get();

        // Assert
        assert reservation.isPending();
    }

    private void assertThereIsReservationForCustomer(String reservationId, String email) {
        // Arrange & Act
        Reservation reservation = reservationStorage.load(reservationId).get();

        // Assert
        assertEquals(email, reservation.getClientData().getEmail());
    }

    private void assertThereIsReservationForCustomerForTotal(String reservationId, BigDecimal total) {
        // Arrange & Act
        Reservation reservation = reservationStorage.load(reservationId).get();

        // Assert
        assert total.compareTo(reservation.getTotal()) == 0;
    }

    private void assertThereIsReservation(String reservationId) {
        // Arrange & Act
        Optional<Reservation> reservation = reservationStorage.load(reservationId);

        // Assert
        assert reservation.isPresent();
    }

    @Test
    void itAllowsToAcceptOffer() {
        Sales sales = thereIsSalesModule();
        String product = thereIsProduct("Lego set", BigDecimal.valueOf(10.10));
        String customerId = thereIsCustomer("Kuba");

        sales.addToCart(customerId, product);
        sales.addToCart(customerId, product);

        OfferAcceptanceRequest request = new OfferAcceptanceRequest();
        request.setFirstname("John").setLastname("Jason").setEmail("john@example.com");

        ReservationDetails details = sales.acceptOffer(customerId, request);

        assertNotNull(details.getReservationId());
        assertNotNull(details.getPaymentUrl());
        assertPaymentGatewayWasInvoked();
        assertThereIsReservation(details.getReservationId());
        assertReservationIsPending(details.getReservationId());
        assertThereIsReservationForCustomer(details.getReservationId(), "john@example.com");
        assertThereIsReservationForCustomerForTotal(details.getReservationId(), BigDecimal.valueOf(20.20));
    }

    private String thereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        productDetails.add(new ProductDetails(id, name, price));
        return id;
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private void assertPaymentGatewayWasInvoked() {
        assertEquals(1, this.paymentGateway.getRequestsCount());
    }

    private Sales thereIsSalesModule() {
        return new Sales(
                cartStorage,
                productDetails,
                new OfferCalculator(productDetails),
                paymentGateway,
                reservationStorage);
    }
}