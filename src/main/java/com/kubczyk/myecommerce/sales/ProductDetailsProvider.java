package com.kubczyk.myecommerce.sales;

import com.kubczyk.myecommerce.productcatalog.HashMapProductStorage;
import com.kubczyk.myecommerce.productcatalog.ProductCatalog;

import java.util.Optional;

public class ProductDetailsProvider {

    public Optional<ProductDetails> loadCartForProduct(String productId) {

        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        ProductDetails productDetails = new ProductDetails(
            productCatalog.loadById(productId).getId(),
            productCatalog.loadById(productId).getName(),
            productCatalog.loadById(productId).getPrice()
        );

        return Optional.of(productDetails);
    }
}
