package com.kubczyk.myecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.kubczyk.myecommerce.payu.PayU;
import com.kubczyk.myecommerce.payu.PayUAPICredentials;
import com.kubczyk.myecommerce.productcatalog.HashMapProductStorage;
import com.kubczyk.myecommerce.productcatalog.ProductCatalog;
import com.kubczyk.myecommerce.sales.Sales;
import com.kubczyk.myecommerce.sales.cart.CartStorage;
import com.kubczyk.myecommerce.sales.offering.OfferCalculator;
import com.kubczyk.myecommerce.sales.payment.PaymentGateway;
import com.kubczyk.myecommerce.sales.payment.PayUPaymentGateway;
import com.kubczyk.myecommerce.sales.productdetails.ProductCatalogProductDetailsProvider;
import com.kubczyk.myecommerce.sales.productdetails.ProductDetailsProvider;
import com.kubczyk.myecommerce.sales.reservation.InMemoryReservationStorage;
import com.kubczyk.myecommerce.web.SessionCurrentCustomerContext;

import java.math.BigDecimal;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createProductCatalog() {

        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        String product1 = productCatalog.addProduct("Item 1",
                "Laborum neque magnam sed ipsa. Voluptate fugiat sit quia ab voluptates ut. Nostrum aut aut sit occaecati iste.");
        productCatalog.assignImage(product1, "images/p1.jpeg");
        productCatalog.changePrice(product1, BigDecimal.valueOf(20.99));
        productCatalog.publishProduct(product1);

        String product2 = productCatalog.addProduct("Item 2",
                "Voluptatem voluptas pariatur ipsa quo ut nulla laudantium ullam. Et est aut qui. Earum adipisci consectetur ea quasi vel odio perspiciatis quis.");
        productCatalog.assignImage(product2, "images/p2.jpeg");
        productCatalog.changePrice(product2, BigDecimal.valueOf(15.74));
        productCatalog.publishProduct(product2);

        String product3 = productCatalog.addProduct("Item 3",
                "Voluptatibus quod ipsam omnis quaerat voluptatem omnis et.");
        productCatalog.assignImage(product3, "images/p3.jpeg");
        productCatalog.changePrice(product3, BigDecimal.valueOf(10.99));
        productCatalog.publishProduct(product3);

        String product4 = productCatalog.addProduct("Item 4", "Omnis ea harum corporis iste quia veritatis esse.");
        productCatalog.assignImage(product4, "images/p4.jpeg");
        productCatalog.changePrice(product4, BigDecimal.valueOf(5.99));
        productCatalog.publishProduct(product4);

        String product5 = productCatalog.addProduct("Item 5",
                "Earum adipisci consectetur ea quasi vel odio perspiciatis quis.");
        productCatalog.assignImage(product5, "images/p5.jpeg");
        productCatalog.changePrice(product5, BigDecimal.valueOf(2.99));
        productCatalog.publishProduct(product5);

        return productCatalog;
    }

    @Bean
    Sales createSalesViaObject(ProductDetailsProvider productDetailsProvider, PaymentGateway paymentGateway) {
        return new Sales(
                new CartStorage(),
                productDetailsProvider,
                new OfferCalculator(productDetailsProvider),
                paymentGateway,
                new InMemoryReservationStorage());
    }

    @Bean
    PaymentGateway createPaymentGateway() {
        return new PayUPaymentGateway(new PayU(PayUAPICredentials.sandbox(), new RestTemplate()));
    }

    @Bean
    SessionCurrentCustomerContext currentCustomerContext(HttpSession httpSession) {
        return new SessionCurrentCustomerContext(httpSession);
    }

    @Bean
    ProductDetailsProvider createProductDetailsProvider(ProductCatalog catalog) {
        return new ProductCatalogProductDetailsProvider(catalog);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
