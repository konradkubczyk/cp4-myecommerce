package com.kubczyk.myecommerce.productcatalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageKey;
    private boolean isPublished;

    public Product(UUID productId, String name, String description) {
        this.productId = productId;
        this.name = name;
        this.description = name;
    }

    public String getId() {
        return this.productId.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        if (published && (price == null || imageKey == null)) {
            throw new ProductCantBePublished();
        }
        isPublished = published;
    }
}
