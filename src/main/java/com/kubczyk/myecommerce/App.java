package com.kubczyk.myecommerce;

import com.kubczyk.myecommerce.productcatalog.HashMapProductStorage;
import com.kubczyk.myecommerce.productcatalog.ProductCatalog;
import com.kubczyk.myecommerce.sales.CartStorage;
import com.kubczyk.myecommerce.sales.OfferMaker;
import com.kubczyk.myecommerce.sales.ProductDetailsProvider;
import com.kubczyk.myecommerce.sales.Sales;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createProductCatalog() {

        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        String product1 = productCatalog.addProduct("My ebook", "nice one");
        productCatalog.assignImage(product1, "images/ebook.jpeg");
        productCatalog.changePrice(product1, BigDecimal.valueOf(20.20));
        productCatalog.publishProduct(product1);

        String product2 = productCatalog.addProduct("Other ebook", "even nicer");
        productCatalog.assignImage(product2, "images/ebook.jpeg");
        productCatalog.changePrice(product2, BigDecimal.valueOf(30.20));
        productCatalog.publishProduct(product2);

        return productCatalog;
    }

    @Bean
    Sales createSales() {
        return new Sales(new CartStorage(), new ProductDetailsProvider());
//        return new Sales(new CartStorage(), new ProductDetailsProvider(), new OfferMaker());
    }
}
