public abstract class Account
{
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static long accountCounter = 0;

    private static String generateAccountNumber()
    {
        accountCounter++;
        String newAccountNumber = String.format("AC%04d", accountCounter);
        return newAccountNumber;
    }

    public Account(String ownerName, long balance)
    {
        this.ownerName = ownerName;
        this.balance = balance;
        this.accountNumber = generateAccountNumber();
        this.active = true;
    }

    public Account(String nm)
    {
        this(nm, 0);
    }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public boolean deposit(long amount)
    {
        if (amount > 0)
        {
            this.balance += amount;
            return true;
        }
        else
        {
            System.out.println("Enter proper amount.");
            return false;
        }
    }

    public boolean withdraw(long amount)
    {
        if (amount < 0)
        {
            System.out.println("Enter proper amount.");
            return false;
        }
        
        boolean allowed = canWithdraw(amount);
        if (!allowed)
        {
            System.out.println("Withdrawal Not allowed: Insufficient amount or locked account.");
            return false;
        }
        else
        {
            this.balance -= amount;
            return true;
        }
    }

    public String getAccountNumber()
    {
        String accNum = this.accountNumber;
        return accNum;
    }

    public String getOwnerName()
    {
        String name = this.ownerName;
        return name;
    }

    public long getBalance()
    {
        long bal = this.balance;
        return bal;
    }

    public boolean getActivity()
    {
        boolean isActive = this.active;
        return isActive;
    }

    @Override
    public String toString()
    {
        String accountDetails = accountNumber + ": owner=" + ownerName + ", balance=" + balance;
        return accountDetails;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        boolean isNotAccount = !(obj instanceof Account);
        if (isNotAccount)
        {
            return false;
        }
        
        Account account = (Account) obj;
        boolean isSameAccount = (accountNumber != null) && accountNumber.equals(account.accountNumber);
        return isSameAccount;
    }

    @Override
    public int hashCode()
    {
        if (accountNumber == null)
        {
            return 0;
        }
        
        int hash = accountNumber.hashCode();
        return hash;
    }
}
class SavingsAccount extends Account {
    private long minBalance;

    public SavingsAccount(String ownerName, long balance, long minBalance) {
        super(ownerName, balance); 
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        double rate = 4.0;
        return rate;
    }

    @Override
    public boolean canWithdraw(long amount) {
        long remainingBalance = getBalance() - amount;
        boolean isAllowed = remainingBalance >= minBalance;
        return isAllowed;
    }
}
class CurrentAccount extends Account {
    private long overDraftLimit;

    public CurrentAccount(String ownerName, long balance, long overDraftLimit) {
        super(ownerName, balance); 
        this.overDraftLimit = overDraftLimit;
    }

    @Override
    public double interestRate() {
        double rate = 0.0;
        return rate;
    }

    @Override
    public boolean canWithdraw(long amount) {
        long remainingBalance = getBalance() - amount;
        long limit = -overDraftLimit;
        boolean isAllowed = remainingBalance >= limit;
        return isAllowed;
    }
}
class FixedDepositAccount extends Account {
    
    public FixedDepositAccount(String ownerName, long balance) {
        super(ownerName, balance); 
    }

    @Override
    public double interestRate() {
        double rate = 7.0;
        return rate;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false; 
    }
}