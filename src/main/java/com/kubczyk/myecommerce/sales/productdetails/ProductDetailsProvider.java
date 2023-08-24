package com.kubczyk.myecommerce.sales.productdetails;

import java.util.Optional;

public interface ProductDetailsProvider {
    Optional<ProductDetails> load(String productId);
}
