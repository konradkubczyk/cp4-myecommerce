package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.sales.cart.Cart;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.EveryNItemLineDiscountPolicy;
import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.offering.TotalDiscountPolicy;
import com.kubczyk.myecommerce.sales.product.NoSuchProductException;
import com.kubczyk.myecommerce.sales.product.ProductDetails;
import com.kubczyk.myecommerce.sales.product.ProductDetailsProvider;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {

    private final CartStorage cartStorage;
    private final ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;


    public Sales(CartStorage cartStorage, ProductDetailsProvider productDetails, OfferCalculator offerCalculator) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetails;
        this.offerCalculator = offerCalculator;
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
            new EveryNItemLineDiscountPolicy(5)
        );
    }


    public void acceptOffer() {}
// TODO: Fix this
//    public PaymentData acceptOffer(String customerId) {
//
//        Offer offer = getCurrentOffer(customerId);
//
//        Reservation reservation = Reservation.from(offer);
//
//        reservationStorage.save(reservation);
//
//        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
//        BigDecimal totalAmountAsGrosze = offer.getTotal().multiply(BigDecimal.valueOf(100));
//        orderCreateRequest.setBuyer(new Buyer()
//                .setEmail(request.email)
//                .setFirstName(request.firstName)
//                .setLastName(request.lastName)
//        );
//        OrderCreateResponse.setDescription("Order description");
//
//        OrderCreateResponse response = payu.handle(orderCreateRequest);
//
//        return new PaymentData(response.getRedirectUri());
//    }

    private Optional<ProductDetails> loadProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadCartForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }
}
