package com.kubczyk.myecommerce.sales;

import java.util.Collection;

public class Cart {
    public static Cart empty() {
        return new Cart();
    }

    public void add(ProductDetails product) {

    }

    public Collection<Object> getProducts() {
        return null;
    }

    public int itemsCount() {
        return 0;
    }
}
