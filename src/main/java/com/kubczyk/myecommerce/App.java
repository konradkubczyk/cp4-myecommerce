package com.kubczyk.myecommerce;

import com.kubczyk.myecommerce.productcatalog.HashMapProductStorage;
import com.kubczyk.myecommerce.productcatalog.ProductCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());
        return productCatalog;
    }
}
