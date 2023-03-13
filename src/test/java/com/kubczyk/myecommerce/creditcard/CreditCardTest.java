package com.kubczyk.myecommerce.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CreditCardTest {

    @Test
    void itAllowsToAssignCreditLimit() {
        // Arrange
        CreditCard card1 = new CreditCard("1234-5678");
        CreditCard card2 = new CreditCard("1234-5678");

        // Act
        card1.assignLimit(BigDecimal.valueOf(1000));
        card2.assignLimit(BigDecimal.valueOf(10000));

        // Assert
        assertEquals(BigDecimal.valueOf(1000), card1.getBalance());
        assertEquals(BigDecimal.valueOf(10000), card2.getBalance());
    }

    @Test
    void idCantAssignLimitBelow100() {
        CreditCard card = new CreditCard("1234-5678");

        try {
            card.assignLimit(BigDecimal.valueOf(50));
            fail("Should throw exception");
        } catch (CreditBelowThresholdException e) {
            assertTrue(true);
        }

        // Alternative
        assertThrows(CreditBelowThresholdException.class, () -> card.assignLimit(BigDecimal.valueOf(50)));
    }

    @Test
    void testDoubleAndFloats() {
        // double x1 = 0.03;
        // double x2 = 0.01;
        // double result = x1 - x2;
        // System.out.println(result);
    }
}
