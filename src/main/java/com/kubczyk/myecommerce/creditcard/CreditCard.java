package com.kubczyk.myecommerce.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private final String cardNumber;
    private BigDecimal balance;
    private BigDecimal credit;
    private int withdrawals = 0;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void assignLimit(BigDecimal creditAmount) {
        if (isCreditAlreadyAssigned()) {
            throw new LimitAlreadyAssignedException();
        }

        if (isBelowCreditThreshold(creditAmount)) {
            throw new CreditBelowThresholdException();
        }

        this.balance = creditAmount;
        this.credit = creditAmount;
    }

    private boolean isCreditAlreadyAssigned() {
        return credit != null;
    }

    private static boolean isBelowCreditThreshold(BigDecimal creditAmount) {
        return creditAmount.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(credit) > 0) {
            throw new WithdrawalAmountOverLimitException();
        }
        if (amount.compareTo(balance) > 0) {
            throw new WithdrawalAmountOverBalanceException();
        }
        if (withdrawals == 10) {
            throw new TooManyWithdrawalsInBillingCycleException();
        }
        withdrawals++;
        balance = balance.subtract(amount);
    }
}
