package model;

public class SavingsAccount extends Account {
    private long minimumBalance;

    public SavingsAccount(String ownerName, long balance, long minimumBalance) {
        super(ownerName, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minimumBalance;
    }
}
