package com.kubczyk.myecommerce.productcatalog;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageFilename;
    private boolean isPublished;

    public Product(UUID productId, String name, String description) {
        this.productId = productId;
        this.name = name;
        this.description = name;
    }

    public String getId() {
        return this.productId.toString();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        if (published && (price == null || imageFilename == null)) {
            throw new ProductDetailsMissingException();
        }
        isPublished = published;
    }
}
