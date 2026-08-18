
import java.util.Scanner;

public class MiniBank {

    record BankInfo(String bankName, String branchName) {

    }

    enum Menu {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        VIEW_ACCOUNT_DETAILS,
        EXIT
    }

    enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    record Command(TransactionType type, String accountNumber, long amount) {

    }

    public static void main(String[] args) {
        BankInfo myBank = new BankInfo("SBI", "Anand");

        Account[] accounts = new Account[10];
        accounts[0] = new Account("Alice", 1000);
        accounts[1] = new Account("Bob", 500);
        accounts[2] = new Account("Charlie");
        int accountCount = 3;

        accounts[0].deposit(500);
        accounts[1].withdraw(200);
        accounts[2].deposit(1000);
        accounts[2].withdraw(300);

        for (int i = 0; i < accountCount; i++) {
            System.out.println(accounts[i]);
        }

        System.out.println("Account 0 equals Account 1: " + accounts[0].equals(accounts[1]));
        Object firstAccount = accounts[0];
        System.out.println("firstAccount is Account: " + (firstAccount instanceof Account));

        System.out.println("Gyan Patel = 25DCS080 4:26pm\n");

        System.out.println("\n--- Mobile Validator ---");
        String validMobile = "9876543210";
        String invalidMobile = "1234567890";
        System.out.println("Valid Mobile (9876543210): " + Validator.isValidMobile(validMobile));
        System.out.println("Invalid Mobile (1234567890): " + Validator.isValidMobile(invalidMobile));

        System.out.println("\n--- Email Validator ---");
        String validEmail = "john@example.com";
        String invalidEmail = "invalid-email";
        System.out.println("Valid Email (john@example.com): " + Validator.isValidEmail(validEmail));
        System.out.println("Invalid Email (invalid-email): " + Validator.isValidEmail(invalidEmail));

        System.out.println("\n--- PAN Validator ---");
        String validPan = "AAAAA1234A";
        String invalidPan = "AAAA12345";
        System.out.println("Valid PAN (AAAAA1234A): " + Validator.isValidPan(validPan));
        System.out.println("Invalid PAN (AAAA12345): " + Validator.isValidPan(invalidPan));

        System.out.println("\n--- IFSC Validator ---");
        String validIfsc = "SBIN0001234";
        String invalidIfsc = "SBI01234567";
        System.out.println("Valid IFSC (SBIN0001234): " + Validator.isValidIfsc(validIfsc));
        System.out.println("Invalid IFSC (SBI01234567): " + Validator.isValidIfsc(invalidIfsc));

            System.out.println("\nGyan Patel = 25DCS080 4:26pm\n");

        System.out.println("\n\t\t TESTING COMMAND PARSER \n");
        
            String commandLine = "DEPOSIT AC0001 500";
            Command cmd = CommandParser.parse(commandLine);
            System.out.println("\nParsed Command: " + commandLine);
            System.out.println("  Type: " + cmd.type());
            System.out.println("  Account Number: " + cmd.accountNumber());
            System.out.println("  Amount: " + cmd.amount());

        System.out.println("\n\t\t TESTING STATEMENT FORMATTER \n");
        System.out.println(ValidStatement.buildStatement(accounts[0]));

        int choice = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("\n\t\tWelcome to " + myBank.bankName() + " , Branch : " + myBank.branchName());

        do {
            System.out.println("1. " + Menu.OPEN_ACCOUNT);
            System.out.println("2. " + Menu.DEPOSIT);
            System.out.println("3. " + Menu.WITHDRAW);
            System.out.println("4. " + Menu.TRANSFER);
            System.out.println("5. " + Menu.VIEW_ACCOUNT_DETAILS);
            System.out.println("6. " + Menu.EXIT);
            System.out.print("Enter Your Choice : ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: {
                    System.out.print("Enter Account Holder Name : ");
                    String holderName = input.nextLine();
                    System.out.print("Enter Initial Balance : ");
                    long initialBalance = input.nextLong();
                    input.nextLine();

                    Account newAccount = new Account(holderName, initialBalance);

                    if (accountCount == accounts.length) {
                        Account[] newArray = new Account[accounts.length * 2];
                        System.arraycopy(accounts, 0, newArray, 0, accounts.length);
                        accounts = newArray;
                    }

                    accounts[accountCount] = newAccount;
                    accountCount++;

                    System.out.println("Account created successfully!");
                    System.out.println(newAccount);
                    break;
                }
                case 2: {
                    System.out.print("Enter account number : ");
                    String accountNumber = input.nextLine();
                    Account account = null;
                    for (Account a : accounts) {
                        if (a != null && a.getAccountNumber().equals(accountNumber)) {
                            account = a;
                            break;
                        }
                    }
                    if (account != null) {
                        System.out.print("How much amount you want to deposit : ");
                        long amount = input.nextLong();
                        input.nextLine();
                        if (account.deposit(amount)) {
                            System.out.println("Deposit successful. New balance: " + account.getBalance());
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter account number : ");
                    String accountNumber = input.nextLine();
                    Account account = null;
                    for (Account a : accounts) {
                        if (a != null && a.getAccountNumber().equals(accountNumber)) {
                            account = a;
                            break;
                        }
                    }
                    if (account != null) {
                        System.out.print("How much amount you want to withdraw : ");
                        long amount = input.nextLong();
                        input.nextLine();
                        if (account.withdraw(amount)) {
                            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Enter sender account number : ");
                    String senderAccountNumber = input.nextLine();
                    Account sender = null;
                    for (Account a : accounts) {
                        if (a != null && a.getAccountNumber().equals(senderAccountNumber)) {
                            sender = a;
                            break;
                        }
                    }
                    if (sender != null) {
                        System.out.print("Enter receiver account number : ");
                        String receiverAccountNumber = input.nextLine();
                        Account receiver = null;
                        for (Account a : accounts) {
                            if (a != null && a.getAccountNumber().equals(receiverAccountNumber)) {
                                receiver = a;
                                break;
                            }
                        }
                        if (receiver != null) {
                            if (sender == receiver) {
                                System.out.println("Sender and receiver cannot be the same account.");
                            } else {
                                System.out.print("How much amount you want to transfer : ");
                                long amount = input.nextLong();
                                input.nextLine();
                                if (amount > 0 && sender.withdraw(amount)) {
                                    receiver.deposit(amount);
                                    System.out.println("Transfer successful. Sender balance: " + sender.getBalance()
                                            + ", Receiver balance: " + receiver.getBalance());
                                }
                            }
                        } else {
                            System.out.println("Account not found.");
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter account number to view details: ");
                    String accountNumber = input.nextLine();
                    Account account = null;
                    for (Account a : accounts) {
                        if (a != null && a.getAccountNumber().equals(accountNumber)) {
                            account = a;
                            break;
                        }
                    }
                    if (account != null) {
                        System.out.println("\n--- Account Details ---");
                        System.out.println(account);
                        System.out.println();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                }
                case 6: {
                    System.out.println("\t\t\tExiting ....\n\t\tThank you for visiting...\n");
                    break;
                }
            }
        } while (choice != 6);

        input.close();
    }
}