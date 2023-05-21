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
        catalog.addProduct("lego set 8083", "nice one");

        // Assert
        List<Product> products = catalog.allProducts();
        assert 1 == products.size();
    }

    @Test
    void itAllowsToLoadProductDetails() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("PC 2", "It finally has a successor! :o");

        catalog.changePrice(productId, BigDecimal.valueOf(20.20));

        Product loaded = catalog.loadById(productId);
        assertEquals(BigDecimal.valueOf(20.20), loaded.getPrice());
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

        assertDoesNotThrow(() -> catalog.assignImage(productId, "happy_elephant.webp"));
        assertEquals("happy_elephant.webp", catalog.loadById(productId).getImageKey());
    }

    @Test
    void itAllowsToPublishProduct() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Clay", "Make of it what you want...");
        catalog.changePrice(productId, BigDecimal.valueOf(96));
        catalog.assignImage(productId, "amazing_clay.webp");

        assertDoesNotThrow(() -> catalog.publishProduct(productId));
        assertTrue(catalog.loadById(productId).isPublished());
    }

    @Test
    void publicationIsPossibleWhenPriceAndImageAreDefined() {

        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("Donkey", "He is gray");

        assertThrows(ProductCantBePublishedException.class, () -> catalog.publishProduct(productId));
        catalog.changePrice(productId, BigDecimal.valueOf(100));
        assertThrows(ProductCantBePublishedException.class, () -> catalog.publishProduct(productId));
        catalog.assignImage(productId, "happy_donkey.webp");
        assertFalse(catalog.loadById(productId).isPublished());
        assertDoesNotThrow(() -> catalog.publishProduct(productId));
        assertTrue(catalog.loadById(productId).isPublished());
    }

    @Test
    void itDoesNotShowDraftProducts() {

        ProductCatalog catalog = thereIsProductCatalog();
        catalog.addProduct("SomeBlocks", "Literally.");

        assertEquals(0, catalog.allPublishedProducts().size());
    }

    private void assertListIsEmpty(List<Product> products) {
        assert 0 == products.size();
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog(new HashMapProductStorage());
    }
}
