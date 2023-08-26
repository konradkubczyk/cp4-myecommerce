package com.kubczyk.myecommerce.sales.reservation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.payment.PaymentData;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reservation {

    @Id
    private String id;

    @Embedded
    private ClientData clientData;

    private BigDecimal total;
    private String paymentId;
    private String paymentUrl;
    private Instant paidAt;
    private Instant createdAt;

    public static Reservation of(OfferAcceptanceRequest request, Offer offer, PaymentData payment) {
        Reservation reservation = new Reservation();

        reservation.id = UUID.randomUUID().toString();
        reservation.clientData = new ClientData(request.getFirstname(), request.getLastname(), request.getEmail());
        reservation.total = offer.getTotal();
        reservation.paymentId = payment.getId();
        reservation.paymentUrl = payment.getUrl();
        reservation.createdAt = Instant.now();

        return reservation;
    }

    public String getId() {
        return id;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public ClientData getClientData() {
        return clientData;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isPending() {
        return paidAt == null;
    }
}
