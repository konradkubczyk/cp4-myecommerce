package com.kubczyk.myecommerce.creditcard;

import org.junit.jupiter.api.Test;

public class NumericRepresentationTest {

    @Test
    void letsCheckDouble() {
        double a = 0.003;
        double b = 0.001;
        double c = b - a;
        System.out.println(c);
    }

    @Test
    void letsCheckFloats() {
        float a = 0.003f;
        float b = 0.001f;
        float c = b - a;
        System.out.println(c);
    }
}
