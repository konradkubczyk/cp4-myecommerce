package com.kubczyk.myecommerce.sales;

import org.springframework.web.bind.annotation.*;

import com.kubczyk.myecommerce.web.CurrentCustomerContext;
import com.kubczyk.myecommerce.web.SessionCurrentCustomerContext;
import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.reservation.ReservationDetails;
import com.kubczyk.myecommerce.sales.reservation.OfferAcceptanceRequest;

@RestController
public class SalesController {

    private Sales sales;
    private CurrentCustomerContext currentCustomerContext;

    public SalesController(Sales sales, CurrentCustomerContext currentCustomerContext) {
        this.sales = sales;
        this.currentCustomerContext = currentCustomerContext;
    }

    @PostMapping("/api/cart/{productId}")
    public void addToCart(@PathVariable String productId) {
        sales.addToCart(getCurrentCustomer(), productId);
    }

    @GetMapping("/api/current-offer")
    public Offer currentOffer() {
        return sales.getCurrentOffer(getCurrentCustomer());
    }

    @GetMapping("/api/current-customer")
    public String getCurrentCustomerId() {
        return currentCustomerContext.getCurrentCustomerId();
    }

    private String getCurrentCustomer() {
        return currentCustomerContext.getCurrentCustomerId();
    }

    @PostMapping("/api/accept-offer")
    public ReservationDetails acceptOffer(@RequestBody OfferAcceptanceRequest request) {
        return sales.acceptOffer(getCurrentCustomerId(), request);
    }
}
