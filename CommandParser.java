public class CommandParser {
    public static MiniBank.Command parse(String line) {
        String[] parts = line.trim().split(" ");

        MiniBank.TransactionType type = MiniBank.TransactionType.valueOf(parts[0].toUpperCase());
        String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);

        return new MiniBank.Command(type, accountNumber, amount);
    }
}