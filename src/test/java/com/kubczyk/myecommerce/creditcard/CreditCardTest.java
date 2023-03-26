package com.kubczyk.myecommerce.creditcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CreditCardTest {

    @Test
    void itAllowsToAssignCreditLimit() {
        // Arrange
        CreditCard card1 = new CreditCard("1234-5678");
        CreditCard card2 = new CreditCard("1234-5678");

        // Act
        card1.assignLimit(BigDecimal.valueOf(1000));
        card2.assignLimit(BigDecimal.valueOf(10000));

        // Assert
        assertEquals(BigDecimal.valueOf(1000), card1.getBalance());
        assertEquals(BigDecimal.valueOf(10000), card2.getBalance());
    }

    @Test
    void itCantAssignLimitBelow100V1() {
        CreditCard card = new CreditCard("1234-5678");

        try {
            card.assignLimit(BigDecimal.valueOf(50));
            fail("Should throw exception");
        } catch (CreditBelowThresholdException e) {
            assertTrue(true);
        }
    }

    @Test
    void itCantAssignLimitBelow100V2() {
        CreditCard card1 = new CreditCard("1234-5678");
        CreditCard card2 = new CreditCard("1234-5678");
        CreditCard card3 = new CreditCard("1234-5678");

        assertThrows(CreditBelowThresholdException.class, () -> card1.assignLimit(BigDecimal.valueOf(50)));

        assertThrows(CreditBelowThresholdException.class, () -> card2.assignLimit(BigDecimal.valueOf(99)));

        assertDoesNotThrow(() -> card3.assignLimit(BigDecimal.valueOf(100)));
    }

    @Test
    void cantAssignLimitTwice() {
        CreditCard card = new CreditCard("1234-5678");

        assertDoesNotThrow(() -> card.assignLimit(BigDecimal.valueOf(777)));
        assertThrows(LimitAlreadyAssignedException.class, () -> card.assignLimit(BigDecimal.valueOf(1000)));
    }

    @Test
    void itAllowsToWithdraw() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(1000));

        card.withdraw(BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(900), card.getBalance());

        assertDoesNotThrow(() -> card.withdraw(BigDecimal.valueOf(900)));
        assertThrows(WithdrawalAmountOverBalanceException.class, () -> card.withdraw(BigDecimal.valueOf(1)));
        assertThrows(WithdrawalAmountOverLimitException.class, () -> card.withdraw(BigDecimal.valueOf(1001)));
    }

    @Test
    void itPreventsMoreThan10WithdrawalsInBillingCycle() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(1000));

        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));
        card.withdraw(BigDecimal.valueOf(10));

        assertDoesNotThrow(() -> card.withdraw(BigDecimal.valueOf(10)));
        assertThrows(TooManyWithdrawalsInBillingCycleException.class, () -> card.withdraw(BigDecimal.valueOf(10)));
        assertEquals(BigDecimal.valueOf(900), card.getBalance());
    }

    @Test
    void itAllowsToRepay() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(100));

        card.withdraw(BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(0), card.getBalance());
        assertDoesNotThrow(() -> card.repay(BigDecimal.valueOf(100)));
        assertEquals(BigDecimal.valueOf(100), card.getBalance());
    }

    @Test
    void itAllowsToCloseBillingCycle() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(1000));

        for (int i = 0; i < 10; i++) {
            card.withdraw(BigDecimal.valueOf(10));
        }

        assertThrows(TooManyWithdrawalsInBillingCycleException.class, () -> card.withdraw(BigDecimal.valueOf(10)));
        card.closeBillingCycle();
        assertDoesNotThrow(() -> card.withdraw(BigDecimal.valueOf(10)));
        assertEquals(BigDecimal.valueOf(890), card.getBalance());
    }

    @Test
    void itAllowsToCheckWithdrawalReport() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(1000));

        card.withdraw(BigDecimal.valueOf(10));
        card.closeBillingCycle();
        card.withdraw(BigDecimal.valueOf(20));
        card.closeBillingCycle();
        card.withdraw(BigDecimal.valueOf(30));

        assertDoesNotThrow(() -> card.getWithdrawalReport().get(0).getDate());
        assertEquals(BigDecimal.valueOf(30), card.getWithdrawalReport().get(0).getAmount());
        assertThrows(IndexOutOfBoundsException.class, () -> card.getWithdrawalReport().get(1).getAmount());
    }

    @Test
    void itAllowsToCheckWithdrawalReportForSpecificBillingCycle() {
        CreditCard card = new CreditCard("1234-5678");
        card.assignLimit(BigDecimal.valueOf(1000));

        card.withdraw(BigDecimal.valueOf(10));
        card.closeBillingCycle();
        card.withdraw(BigDecimal.valueOf(20));
        card.withdraw(BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(10), card.getWithdrawalReport(0).get(0).getAmount());
        assertEquals(BigDecimal.valueOf(30), card.getWithdrawalReport(1).get(1).getAmount());
        assertThrows(IndexOutOfBoundsException.class, () -> card.getWithdrawalReport(2));
    }
}
