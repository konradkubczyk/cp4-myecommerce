package com.kubczyk.myecommerce.sales.reservation;

public class OfferAcceptanceRequest {

    String email;
    String firstname;
    String lastname;

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public OfferAcceptanceRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public OfferAcceptanceRequest setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public OfferAcceptanceRequest setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
}
