package com.kubczyk.myecommerce.creditcard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CreditCard {
    private final String cardNumber;
    private BigDecimal balance;
    private BigDecimal credit;

    private ArrayList<BillingCycle> billingCycles = new ArrayList<>(Arrays.asList(new BillingCycle()));

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

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setDate(new Date());
        withdrawal.setAmount(amount);
        billingCycles.get(billingCycles.size() - 1).addWithdrawal(withdrawal);

        this.balance = balance.subtract(amount);
    }

    public void repay(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public void closeBillingCycle() {
        this.billingCycles.add(new BillingCycle());
    }

    public ArrayList<Withdrawal> getWithdrawalReport() {
        return this.getWithdrawalReport(this.billingCycles.size() - 1);
    }

    public ArrayList<Withdrawal> getWithdrawalReport(int billingCycleNumber) {
        return this.billingCycles.get(billingCycleNumber).getWithdrawals();
    }
}
