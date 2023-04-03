package com.kubczyk.myecommerce.productcatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashMapProductStorage implements ProductStorage {
    private final Map<String, Product> products;

    public HashMapProductStorage() {
        this.products = new HashMap<>();
    }

    @Override
    public List<Product> allProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void add(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product loadById(String productId) {
        return products.get(productId);
    }

    @Override
    public List<Product> allPublishedProducts() {
        return products.values()
                .stream()
                .filter(Product::isPublished)
                .collect(Collectors.toList());
    }
}
