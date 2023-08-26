package com.kubczyk.myecommerce.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    String hello() {
        return "Index";
    }
}
