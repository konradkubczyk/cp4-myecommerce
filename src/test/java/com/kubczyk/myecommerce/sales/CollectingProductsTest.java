package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.payu.PayU;
import com.kubczyk.myecommerce.payu.PayUAPICredentials;
import com.kubczyk.myecommerce.sales.cart.Cart;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.Offer;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.offering.OfferLine;
import com.kubczyk.myecommerce.sales.payment.PayUPaymentGateway;
import com.kubczyk.myecommerce.sales.productdetails.ProductDetails;
import com.kubczyk.myecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import com.kubczyk.myecommerce.sales.reservation.InMemoryReservationStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        // Arrange
        Sales sales = thereIsSalesModule();
        String productId = thereIsProduct("Catnip", BigDecimal.valueOf(5.99));
        String customer = thereIsCustomer("Anon");

        // Act
        sales.addToCart(customer, productId);

        // Assert
        assertThereIsNProductsInCustomersCart(customer, 1);
    }

    @Test
    void itAllowsToAddProductByMultipleCustomers() {
        // Arrange
        Sales sales = thereIsSalesModule();
        String productId1 = thereIsProduct("A snack", BigDecimal.valueOf(2.99));
        String productId2 = thereIsProduct("A drink", BigDecimal.valueOf(1.59));
        String productId3 = thereIsProduct("A dessert", BigDecimal.valueOf(3.97));
        String customerId1 = thereIsCustomer("Alice");
        String customerId2 = thereIsCustomer("Bob");

        // Act
        sales.addToCart(customerId1, productId1);
        sales.addToCart(customerId1, productId2);
        sales.addToCart(customerId2, productId3);

        // Assert
        assertThereIsNProductsInCustomersCart(customerId1, 2);
        assertThereIsNProductsInCustomersCart(customerId2, 1);
    }

    @Test
    public void itGeneratesOfferBasedOnCurrentCarts() {
        // Arrange
        Sales sales = thereIsSalesModule();
        String productId1 = thereIsProduct("Book A", BigDecimal.valueOf(17.23));
        String productId2 = thereIsProduct("Book B", BigDecimal.valueOf(15.99));
        String customerId = thereIsCustomer("John");

        // Act
        sales.addToCart(customerId, productId1);
        sales.addToCart(customerId, productId1);
        sales.addToCart(customerId, productId2);
        Offer offer = sales.getCurrentOffer(customerId);

        // Assert
        assertThat(offer.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(50.45));
        assertThat(offer.getOrderLines()).hasSize(2);
        assertThat(offer.getOrderLines()).filteredOn(orderLine -> orderLine.getProductId().equals(productId1))
                .extracting(OfferLine::getQuantity).first().isEqualTo(2);
    }

    private void assertThereIsNProductsInCustomersCart(String customer, int productsCount) {
        Cart customerCart = cartStorage.load(customer).get();
        assert customerCart.getItemsCount() == productsCount;
    }

    private String thereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        productDetails.add(new ProductDetails(id, name, price));
        return id;
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private Sales thereIsSalesModule() {
        return new Sales(
                cartStorage,
                productDetails,
                new OfferCalculator(productDetails),
                new PayUPaymentGateway(new PayU(PayUAPICredentials.sandbox(), new RestTemplate())),
                new InMemoryReservationStorage());
    }
}
