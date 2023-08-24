package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.sales.cart.Cart;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CollectingProductsTest {

    private CartStorage cartStorage;
    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setup() {
        cartStorage = new CartStorage();
        productDetails = new InMemoryProductDetailsProvider();
    }

    @Test
    void itAllowsToCollectProductsToCart() {
        //Arrange
        Sales sales = thereIsSalesModule();
        String productId =  thereIsProduct();
        String customer = thereIsCustomer("Anon");

        //Act
        sales.addToCart(customer, productId);

        //Assert
        assertThereIsNProductsInCustomersCart(customer, 1);
    }

    private void assertThereIsNProductsInCustomersCart(String customer, int productsCount) {
        Cart customerCart = cartStorage.load(customer).get();
        assert customerCart.getItemsCount() == productsCount;
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private String thereIsProduct() {
        return UUID.randomUUID().toString();
    }

    private Sales thereIsSalesModule() {
        return new Sales(
            cartStorage,
            productDetails,
            new OfferCalculator(productDetails)
        );
    }
}
