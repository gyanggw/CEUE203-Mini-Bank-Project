class Account
{
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static long accountCounter = 0;

    private static String generateAccountNumber()
    {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
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
        if (amount > balance)
        {
            System.out.println("You do not have sufficient amount.");
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
        return this.accountNumber;
    }

    public String getOwnerName()
    {
        return this.ownerName;
    }

    public long getBalance()
    {
        return this.balance;
    }

    public boolean getActivity()
    {
        return this.active;
    }

    @Override
    public String toString()
    {
        return accountNumber + ": owner=" + ownerName + ", balance=" + balance;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!(obj instanceof Account))
            return false;
        Account account = (Account) obj;
        return accountNumber != null && accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode()
    {
        return accountNumber == null ? 0 : accountNumber.hashCode();
    }
}
