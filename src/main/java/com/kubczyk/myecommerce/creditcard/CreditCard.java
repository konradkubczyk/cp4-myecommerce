package com.kubczyk.myecommerce.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private final String cardNumber;
    private BigDecimal balance;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void assignLimit(BigDecimal creditAmount) {
        if (creditAmount.compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new CreditBelowThresholdException();
        }
        this.balance = creditAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
