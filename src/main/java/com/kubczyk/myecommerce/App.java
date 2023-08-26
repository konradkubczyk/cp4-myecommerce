package com.kubczyk.myecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
@EnableWebMvc
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createProductCatalog() {

        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        String product1 = productCatalog.addProduct("My ebook", "Nice one");
        productCatalog.assignImage(product1, "images/ebook.jpeg");
        productCatalog.changePrice(product1, BigDecimal.valueOf(20.20));
        productCatalog.publishProduct(product1);

        String product2 = productCatalog.addProduct("Other ebook", "Also pretty good");
        productCatalog.assignImage(product2, "images/ebook.jpeg");
        productCatalog.changePrice(product2, BigDecimal.valueOf(30.20));
        productCatalog.publishProduct(product2);

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
}
