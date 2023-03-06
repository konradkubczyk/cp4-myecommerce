package com.kubczyk.myecommerce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jakub", "Michal", "Kasia", "Maria");

        Greeter greeter = new Greeter();
        greeter.greet("Kuba"); // -> Hello Kuba

        System.out.println();

        // Greet all ladies
        // Hello Kasia
        // Hello Maria

        List<String> ladies = new ArrayList<>();
        for (String name : names) {
            if (name.endsWith("a")) {
                ladies.add(name);
            }
        }

        for (String ladyName : ladies) {
            greeter.greet(ladyName);
        }

        System.out.println();

        names.stream()
                .filter(name -> name.endsWith("a")) // Lambda name: name[-1] == "a"
                .forEach(greeter::greet);

        System.out.println();

        names.stream()
                .filter(name -> name.endsWith("a"))
                .filter(name -> name.startsWith("K"))
                .map(String::toUpperCase)
                .forEach(greeter::greet);
    }
}
