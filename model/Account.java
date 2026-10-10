package model;

import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing {
    private static long accountCounter = 0;

    @Id
    private final String accountNumber;
    private String ownerName;
    @Positive
    private long balance;
    private boolean active;

    public Account(String ownerName, long balance) {
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountNumber = generateAccountNumber();
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    private static String generateAccountNumber() {
        accountCounter = accountCounter + 1;
        return "AC" + String.format("%04d", accountCounter);
    }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Enter a valid amount.");
        }
    }

    public synchronized boolean withdraw(long amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
            return false;
        }

        if (canWithdraw(amount) == false) {
            System.out.println("Withdrawal not allowed.");
            return false;
        }

        balance = balance - amount;
        System.out.println("Withdrawal successful.");
        return true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public synchronized long getBalance() {
        return balance;
    }

    public boolean transferTo(Account destination, long amount) {
        if (destination == null || destination == this || amount <= 0) {
            return false;
        }

        Account first = this;
        Account second = destination;
        // Lock in the same order for every transfer to prevent deadlock.
        if (accountNumber.compareTo(destination.accountNumber) > 0) {
            first = destination;
            second = this;
        }

        synchronized (first) {
            synchronized (second) {
                if (!withdraw(amount)) {
                    return false;
                }
                destination.deposit(amount);
                return true;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Owner: " + ownerName + ", Balance: " + getBalance();
    }
}
