package com.kubczyk.myecommerce.sales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class ReservationStorageTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void insert() {
        String productId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(
                productId,
            BigDecimal.TEN,
            "payment"
        );
        reservationRepository.save(reservation);

        Reservation loaded = reservationRepository.findById(productId).get();
    }

    @Test
    void select() {

    }
}
