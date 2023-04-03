package com.kubczyk.myecommerce.productcatalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductCatalog {

    private List<Product> products;

    public ProductCatalog() {
        this.products = new ArrayList<>();
    }

    public List<Product> allProducts() {
        return products;
    }

    public String addProduct(String name, String description) {
        Product newProduct = new Product(
                UUID.randomUUID(),
                name,
                description
        );

        this.products.add(newProduct);

        return newProduct.getId();
    }

    public Product loadById(String productId) {
        for (Product product : products) {
            if (Objects.equals(product.getId(), productId)) {
                return product;
            }
        }
        throw new ProductNotFoundException();
    }
}
