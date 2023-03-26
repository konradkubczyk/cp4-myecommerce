package com.kubczyk.myecommerce.creditcard;

import java.math.BigDecimal;
import java.util.Date;

public class Withdrawal {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private BigDecimal amount;
}
