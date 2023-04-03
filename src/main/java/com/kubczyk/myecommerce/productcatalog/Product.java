package com.kubczyk.myecommerce.productcatalog;

import java.util.UUID;

public class Product {

    private UUID productId;
    private String name;
    private String description;

    public Product(UUID productId, String name, String description) {
        this.productId = productId;
        this.name = name;
        this.description = name;
    }

    public String getId() {
        return this.productId.toString();
    }
}
