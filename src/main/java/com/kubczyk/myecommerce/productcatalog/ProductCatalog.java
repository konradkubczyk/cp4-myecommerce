package com.kubczyk.myecommerce.productcatalog;

import java.math.BigDecimal;
import java.util.*;

public class ProductCatalog {

    private final ProductStorage productStorage;

    public ProductCatalog(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public List<Product> allProducts(){
        return productStorage.allProducts();
    }

    public ArrayList<Product> loadDatabase(){
        ArrayList<Product> database = new ArrayList<Product>();
        return database;
    }

    public String addProduct(String name, String description){
        Product newProduct = new Product(
                UUID.randomUUID(),
                name,
                description
        );

        productStorage.add(newProduct);

        return newProduct.getId();
    }

    public void changePrice(String id,BigDecimal price){
        loadById(id).setPrice(price);
    }

    public void assignImage(String id,String image){
        loadById(id).setImageKey(image);
    }

    public void changeVisibility(String productId, Boolean isPublished){
        loadById(productId).setPublished(isPublished);
    }

    public Product loadById(String productId) {
        return productStorage.loadById(productId);
    }

    public List<Product> allPublishedProducts() {
        return productStorage.allPublishedProducts();
    }

    public void publishProduct(String productId) {
        Product loaded = loadById(productId);
        if (loaded.getPrice() == null){
            throw new ProductCantBePublishedException();
        }

        if (loaded.getImageKey() == null){
            throw new ProductCantBePublishedException();
        }

        loaded.setPublished(true);
    }
}