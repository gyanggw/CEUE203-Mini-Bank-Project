package service;

import model.Account;

public class TransactionTask implements Runnable {
    private final Account account;
    private final long amount;
    private final boolean deposit;

    public TransactionTask(Account account, long amount, boolean deposit) {
        this.account = account;
        this.amount = amount;
        this.deposit = deposit;
    }

    @Override
    public void run() {
        if (deposit) {
            account.deposit(amount);
        } else {
            account.withdraw(amount);
        }
    }
}
