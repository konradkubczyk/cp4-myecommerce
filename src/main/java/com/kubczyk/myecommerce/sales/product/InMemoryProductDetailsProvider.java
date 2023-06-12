package com.kubczyk.myecommerce.sales.product;

import com.kubczyk.myecommerce.sales.product.ProductDetails;
import com.kubczyk.myecommerce.sales.product.ProductDetailsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductDetailsProvider implements ProductDetailsProvider {

    List<ProductDetails> productDetails;
    public InMemoryProductDetailsProvider() {
        this.productDetails = new ArrayList<>();
    }

    public Optional<ProductDetails> load(String productId) {
        return productDetails.stream().filter(p -> p.getId().equals(productId)).findFirst();
    }

    public void add(ProductDetails details) {
        this.productDetails.add(details);
    }
}
