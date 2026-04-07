package com.example.springbootapi.eBay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTest {

    private BankingSystem bank;

    @BeforeEach
    public void setUp() {
        bank = new BankingSystem();
    }

    // ==================== createAccount ====================

    @Test
    public void testCreateAccount_Success() {
        assertTrue(bank.createAccount("alice", 1));
    }

    @Test
    public void testCreateAccount_DuplicateReturnsFalse() {
        bank.createAccount("alice", 1);
        assertFalse(bank.createAccount("alice", 2));
    }

    @Test
    public void testCreateAccount_MultipleDistinctAccounts() {
        assertTrue(bank.createAccount("alice", 1));
        assertTrue(bank.createAccount("bob", 2));
        assertTrue(bank.createAccount("charlie", 3));
    }

    // ==================== deposit ====================

    @Test
    public void testDeposit_Success() {
        bank.createAccount("alice", 1);
        Optional<Integer> result = bank.deposit("alice", 2, 500);
        assertTrue(result.isPresent());
        assertEquals(500, result.get());
    }

    @Test
    public void testDeposit_MultipleDeposits() {
        bank.createAccount("alice", 1);
        bank.deposit("alice", 2, 200);
        Optional<Integer> result = bank.deposit("alice", 3, 300);
        assertTrue(result.isPresent());
        assertEquals(500, result.get());
    }

    @Test
    public void testDeposit_NonExistentAccountReturnsEmpty() {
        Optional<Integer> result = bank.deposit("ghost", 1, 100);
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeposit_StartsAtZeroBalance() {
        bank.createAccount("alice", 1);
        Optional<Integer> result = bank.deposit("alice", 2, 0);
        assertTrue(result.isPresent());
        assertEquals(0, result.get());
    }

    // ==================== transfer ====================

    @Test
    public void testTransfer_Success() {
        bank.createAccount("alice", 1);
        bank.createAccount("bob", 2);
        bank.deposit("alice", 3, 1000);

        Optional<Integer> result = bank.transfer("alice", "bob", 4, 400);
        assertTrue(result.isPresent());
        assertEquals(600, result.get()); // alice's remaining balance
    }

    @Test
    public void testTransfer_FromNonExistentAccountReturnsEmpty() {
        bank.createAccount("bob", 1);
        Optional<Integer> result = bank.transfer("ghost", "bob", 2, 100);
        assertFalse(result.isPresent());
    }

    @Test
    public void testTransfer_ToNonExistentAccountReturnsEmpty() {
        bank.createAccount("alice", 1);
        bank.deposit("alice", 2, 500);
        Optional<Integer> result = bank.transfer("alice", "ghost", 3, 100);
        assertFalse(result.isPresent());
    }

    @Test
    public void testTransfer_InsufficientFundsReturnsEmpty() {
        bank.createAccount("alice", 1);
        bank.createAccount("bob", 2);
        bank.deposit("alice", 3, 100);

        // balance == amount means <= check fails (cannot fully drain)
        Optional<Integer> result = bank.transfer("alice", "bob", 4, 100);
        assertFalse(result.isPresent());
    }

    @Test
    public void testTransfer_OverdraftReturnsEmpty() {
        bank.createAccount("alice", 1);
        bank.createAccount("bob", 2);
        bank.deposit("alice", 3, 50);

        Optional<Integer> result = bank.transfer("alice", "bob", 4, 200);
        assertFalse(result.isPresent());
    }

    @Test
    public void testTransfer_ReceiverBalanceIncreased() {
        bank.createAccount("alice", 1);
        bank.createAccount("bob", 2);
        bank.deposit("alice", 3, 1000);
        bank.deposit("bob", 4, 100);

        bank.transfer("alice", "bob", 5, 400);

        // Verify bob received the funds via a subsequent deposit check
        Optional<Integer> bobBalance = bank.deposit("bob", 6, 0);
        assertTrue(bobBalance.isPresent());
        assertEquals(500, bobBalance.get()); // 100 + 400
    }

    @Test
    public void testTransfer_BothAccountsMustExist() {
        Optional<Integer> result = bank.transfer("alice", "bob", 1, 100);
        assertFalse(result.isPresent());
    }
}
