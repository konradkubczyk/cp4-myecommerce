package com.kubczyk.myecommerce.playground;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

@SpringBootTest
public class JdbcPlaygroundTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DROP TABLE products IF EXISTS");
        jdbcTemplate.execute(
            "CREATE TABLE `products` (" +
            "`id` varchar(100) NOT NULL," +
            "`name` varchar(100)," +
            "PRIMARY KEY(id)" +
            ")"
        );
    }

    @Test
    void helloWorldViaDb() {
        String result = jdbcTemplate.queryForObject(
            "SELECT 'Hello world'",
            String.class
        );
    }

    @Test
    void insert() {
        String productId = "my_product_1";
        String productName = "Sample product";

        jdbcTemplate.update(
            "INSERT INTO `products` (id, name) values (?, ?)",
            productId,
            productName
        );

        int productsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `product`", Integer.class);

        assert productsCount == 1;
    }

    @Test
    void select() {
        String productId = "my_product_1";
        String productName = "Sample product";

        jdbcTemplate.update(
                "INSERT INTO `products` (id, name) values (?, ?)",
                productId,
                productName
        );

        String querySql = "SELECT * FROM `products` WHERE id = ?";
        HashMap<String, Object> loaded = jdbcTemplate.queryForObject(
            querySql,
            new Object[]{productId},
            (result, identifier) -> {
                HashMap<String, Object> myResult = new HashMap<>();
                myResult.put("product_id", result.getString("id"));
                myResult.put("product_name", result.getString("name"));
                return myResult;
            }
        );
    }
}
