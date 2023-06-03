package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.sales.offering.Offer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {

    Sales sales;

    public SalesController(Sales sales) {
        this.sales = sales;
    }

    @GetMapping("/api/get-current-offer")
    public Offer getCurrentOffer() {
        return sales.getCurrentOffer(getCurrentCustomer());
    }

    @PostMapping("/api/add-to-cart/{productId}")
    public void addToCart(@PathVariable String productId) {
        sales.addToCart(getCurrentCustomer(), productId);
    }

    private String getCurrentCustomer() {
        return "Anon";
    }
}
