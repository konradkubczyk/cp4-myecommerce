package com.kubczyk.myecommerce.playground;

import org.junit.jupiter.api.Test;

public class BaseTestSchemaTest {

    @Test
    void testIt() {
        assert true;
    }

    @Test
    void testIt2() {
        String myName = "Anon";
        String output =  String.format("Hello %s", myName);

        assert output.equals("Hello Anon");
    }

    @Test
    void baseSchema() {
        //Arrange // Given  //  Input
        //Act     // When   //  interaction
        //Assert  // Then   //  Output
    }
}
