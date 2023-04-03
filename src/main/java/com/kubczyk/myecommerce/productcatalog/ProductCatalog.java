package com.kubczyk.myecommerce.productcatalog;

import java.math.BigDecimal;
import java.util.*;

public class ProductCatalog {
    ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public List<Product> allProducts() {
        return productStorage.allProducts();
    }

    public String addProduct(String name, String description) {
        Product newProduct = new Product(
                UUID.randomUUID(),
                name,
                description
        );

        productStorage.add(newProduct);

        return newProduct.getId();
    }

    public Product loadById(String productId) {
        return productStorage.loadById(productId);
    }

    public void changePrice(String productId, BigDecimal price) {
        productStorage.loadById(productId).setPrice(price);
    }

    public void assignImage(String productId, String imageKey) {
        productStorage.loadById(productId).setImageKey(imageKey);
    }

    public void publishProduct(String productId) {
        productStorage.loadById(productId).setPublished(true);
    }

    public List<Product> allPublishedProducts() {
        return productStorage.allPublishedProducts();
    }
}
