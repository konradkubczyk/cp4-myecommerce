package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.sales.cart.Cart;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.EveryNItemLineDiscountPolicy;
import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.offering.TotalDiscountPolicy;
import com.kubczyk.myecommerce.sales.payment.PaymentGateway;
import com.kubczyk.myecommerce.sales.payment.PaymentData;
import com.kubczyk.myecommerce.sales.payment.RegisterPaymentRequest;
import com.kubczyk.myecommerce.sales.productdetails.NoSuchProductException;
import com.kubczyk.myecommerce.sales.productdetails.ProductDetails;
import com.kubczyk.myecommerce.sales.productdetails.ProductDetailsProvider;
import com.kubczyk.myecommerce.sales.reservation.InMemoryReservationStorage;
import com.kubczyk.myecommerce.sales.reservation.OfferAcceptanceRequest;
import com.kubczyk.myecommerce.sales.reservation.Reservation;
import com.kubczyk.myecommerce.sales.reservation.ReservationDetails;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {

    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private InMemoryReservationStorage reservationStorage;

    public Sales(CartStorage cartStorage, ProductDetailsProvider productDetails, OfferCalculator offerCalculator,
            PaymentGateway paymentGateway, InMemoryReservationStorage reservationStorage) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetails;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public void addToCart(String customerId, String productId) {

        Cart customersCart = loadForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = getProductDetails(productId)
                .orElseThrow(NoSuchProductException::new);

        customersCart.add(product.getId());

        cartStorage.save(customerId, customersCart);
    }

    private Optional<ProductDetails> getProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        return this.offerCalculator.calculateOffer(
                customerCart.getCartItems(),
                new TotalDiscountPolicy(BigDecimal.valueOf(500), BigDecimal.valueOf(50)),
                new EveryNItemLineDiscountPolicy(5));
    }

    public ReservationDetails acceptOffer(String customerId, OfferAcceptanceRequest request) {
        Offer offer = this.getCurrentOffer(customerId);
        PaymentData payment = paymentGateway.register(RegisterPaymentRequest.of(request, offer));
        Reservation reservation = Reservation.of(request, offer, payment);
        reservationStorage.save(reservation);
        return new ReservationDetails(reservation.getId(), reservation.getPaymentUrl());
    }

    private Optional<ProductDetails> loadProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadCartForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }
}
