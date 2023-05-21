package com.kubczyk.myecommerce.sales;

import java.math.BigDecimal;

public class Offer {

    BigDecimal total = BigDecimal.ZERO;
    int itemsCount = 0;

    public BigDecimal getTotal() {
        return this.total;
    }

    public int getItemsCount() {
        return this.itemsCount;
    }
}
