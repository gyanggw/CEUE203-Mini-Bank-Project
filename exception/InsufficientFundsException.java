package exception;

public class InsufficientFundsException extends BankException {
    public final long shortfall;

    public InsufficientFundsException(String message, long shortfall) {
        super(message);
        this.shortfall = shortfall;
    }
}
