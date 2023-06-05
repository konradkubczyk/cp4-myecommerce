package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.payu.Buyer;
import com.kubczyk.myecommerce.payu.OrderCreateRequest;
import com.kubczyk.myecommerce.payu.OrderCreateResponse;
import com.kubczyk.myecommerce.sales.cart.Cart;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.product.NoSuchProductException;
import com.kubczyk.myecommerce.sales.product.ProductDetails;
import com.kubczyk.myecommerce.sales.product.ProductDetailsProvider;
import com.kubczyk.myecommerce.sales.reservation.Reservation;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {

    private final CartStorage cartStorage;
    private final ProductDetailsProvider productDetailsProvider;

    public Sales(CartStorage cartStorage, ProductDetailsProvider productDetailsProvider) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetailsProvider;
    }

    public void addToCart(String customerId, String productId) {

        Cart customersCart = loadForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = getProductDetails(productId)
                .orElseThrow(NoSuchProductException::new);

        customersCart.add(product);

        cartStorage.save(customerId, customersCart);
    }

    private Optional<ProductDetails> getProductDetails(String productId) {
        return productDetailsProvider.loadForProduct(productId);
    }

    private Optional<Cart> loadForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String currentCustomer) {
        return new Offer();
    }

    // TODO: Fix this
    public PaymentData acceptOffer(String customerId) {

        Offer offer = getCurrentOffer(customerId);

        Reservation reservation = Reservation.from(offer);

        reservationStorage.save(reservation);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        // TODO: Fix this
        BigDecimal totalAmountAsGrosze = offer.getTotal().multiply(BigDecimal.valueOf(100));
        orderCreateRequest.setBuyer(new Buyer()
                .setEmail(request.email)
                .setFirstName(request.firstName)
                .setLastName(request.lastName)
        );
        OrderCreateResponse.setDescription("Order description");

        OrderCreateResponse response = payu.handle(orderCreateRequest);

        return new PaymentData(response.getRedirectUri());
    }
}
