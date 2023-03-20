package com.kubczyk.myecommerce;

import org.junit.jupiter.api.Test;

public class BaseTestSchemaTest {

    @Test
    void testIt() {
        assert true == true;
    }

    @Test
    void testIt2() {
        String myName = "Konrad";
        String output = String.format("Hello %s", myName);

        System.out.println(output);

        assert output.equals("Hello Konrad");
    }

    @Test
    void baseSchema() {
        // AAA - Arrange Act Assert

        // Arrange - given, input

        // Act - when, interaction

        // Assert - then, output
    }
}
