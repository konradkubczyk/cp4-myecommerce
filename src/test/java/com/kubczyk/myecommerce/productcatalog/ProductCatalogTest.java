package com.kubczyk.myecommerce.productcatalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ProductCatalogTest {

    @Test
    void itAllowsToListMyProducts() {

        // Arrange
        ProductCatalog catalog = thereIsProductCatalog();

        // Act
        List<Product> products = catalog.allProducts();

        // Assert
        assertListIsEmpty(products);
    }

    @Test
    void itAllowsToAddProduct() {
        // Arrange
        ProductCatalog catalog = thereIsProductCatalog();

        // Act
        String productId = catalog.addProduct("lego set 8083", "nice one");

        // Assert
        List<Product> products = catalog.allProducts();
        assert 1 == products.size();
    }

    @Test
    void itAllowsToLoadProductDetails() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("PC 2", "It finally has a successor! :o");

        Product loadedProduct = catalog.loadById(productId);
        assert loadedProduct.getId().equals(productId);
    }

    @Test
    void itAllowsToChangePrice() {
        fail("Not yet implemented");
    }

    @Test
    void itAllowsToAssignImage() {
        fail("Not yet implemented");
    }

    @Test
    void itAllowsToPublishProduct() {
        fail("Not yet implemented");
    }

    @Test
    void publicationIsPossibleWhenPriceAndImageAreDefined() {
        fail("Not yet implemented");
    }

    private void assertListIsEmpty(List<Product> products) {
        assert 0 == products.size();
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog();
    }
}
