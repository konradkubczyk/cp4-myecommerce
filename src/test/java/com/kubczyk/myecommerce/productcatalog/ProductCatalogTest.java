package com.kubczyk.myecommerce.productcatalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Mysterious CD", "Some audio");
        Product loadedProduct = catalog.loadById(productId);

        assertDoesNotThrow(() -> loadedProduct.setPrice(BigDecimal.valueOf(88)));
        assertEquals(BigDecimal.valueOf(88), loadedProduct.getPrice());
    }

    @Test
    void itAllowsToAssignImage() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Elephant", "Big animal :O");
        Product loadedProduct = catalog.loadById(productId);

        assertDoesNotThrow(() -> loadedProduct.setImageFilename("happy_elephant.webp"));
        assertEquals("happy_elephant.webp", loadedProduct.getImageFilename());
    }

    @Test
    void itAllowsToPublishProduct() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Clay", "Make of it what you want...");
        Product loadedProduct = catalog.loadById(productId);
        loadedProduct.setPrice(BigDecimal.valueOf(96));
        loadedProduct.setImageFilename("amazing_clay.webp");

        assertDoesNotThrow(() -> loadedProduct.setPublished(true));
        assertEquals(true, loadedProduct.isPublished());
        assertDoesNotThrow(() -> loadedProduct.setPublished(false));
        assertEquals(false, loadedProduct.isPublished());
    }

    @Test
    void publicationIsPossibleWhenPriceAndImageAreDefined() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Donkey", "He is gray");
        Product loadedProduct = catalog.loadById(productId);

        assertThrows(ProductDetailsMissingException.class, () -> loadedProduct.setPublished(true));
        loadedProduct.setPrice(BigDecimal.valueOf(100));
        assertThrows(ProductDetailsMissingException.class, () -> loadedProduct.setPublished(true));
        loadedProduct.setImageFilename("happy_donkey.webp");
        assertEquals(false, loadedProduct.isPublished());
        assertDoesNotThrow(() -> loadedProduct.setPublished(true));
        assertEquals(true, loadedProduct.isPublished());

    }

    private void assertListIsEmpty(List<Product> products) {
        assert 0 == products.size();
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog();
    }
}
