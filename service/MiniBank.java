package service;

import static util.Validator.isValidEmail;
import static util.Validator.isValidIfsc;
import static util.Validator.isValidMobile;
import static util.Validator.isValidPan;

import java.util.Scanner;

import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.InterestBearing;
import model.Premium;
import model.SavingsAccount;
import model.Transactable;
import model.WithdrawRule;
import util.CommandParser;
import util.ValidStatement;

public class MiniBank {

    public record BankInfo(String bankName, String branchName) {
    }

    public enum Menu {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        VIEW_ACCOUNT_DETAILS,
        EXIT
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    public record Command(TransactionType type, String accountNumber, long amount) {
    }

    public static void main(String[] args) {
        BankInfo bank = new BankInfo("SBI", "Anand");
        System.out.println("Mini Bank Application");
        System.out.println("Bank: " + bank.bankName() + " | Branch: " + bank.branchName());

        Account[] accounts = new Account[3];
        accounts[0] = new SavingsAccount("Gyan", 5000, 1000);
        accounts[1] = new CurrentAccount("Pratham", 2000, 5000);
        accounts[2] = new FixedDepositAccount("Om", 10000);

        for (Account acc : accounts) {
            if (acc != null) {
                System.out.println(acc.getOwnerName() + " has " + acc.getBalance() + " balance and interest " + acc.interestRate() + "%");
            }
        }

        System.out.println("Valid mobile: " + isValidMobile("9876543210"));
        System.out.println("Valid email: " + isValidEmail("john@example.com"));
        System.out.println("Valid PAN: " + isValidPan("AAAAA1234A"));
        System.out.println("Valid IFSC: " + isValidIfsc("SBIN0001234"));

        System.out.println("\nAnonymous class rule test:");
        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.getBalance() >= amount;
            }
        };
        System.out.println("Anonymous rule result: " + anonymousRule.allow(accounts[0], 1000));

        System.out.println("\nLambda rule test:");
        WithdrawRule lambdaRule = (account, amount) -> account.getBalance() >= amount;
        System.out.println("Lambda rule result: " + lambdaRule.allow(accounts[1], 2500));

        System.out.println("\nYearly interest sample:");
        InterestBearing savings = accounts[0];
        System.out.println("Yearly interest for savings: " + savings.yearlyInterest());

        Account premiumAccount = new SavingsAccount("Amit", 20000, 2000);
        if (premiumAccount instanceof Premium) {
            System.out.println("Premium account detected.");
        }

        System.out.println("\nStatement sample:\n" + ValidStatement.buildStatement(accounts[0]));

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Parsed command: " + cmd.type() + " " + cmd.accountNumber() + " " + cmd.amount());

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter holder name: ");
                String name = scanner.nextLine();
                System.out.print("Enter initial balance: ");
                long balance = scanner.nextLong();
                scanner.nextLine();
                Account newAccount = new SavingsAccount(name, balance, 500);
                System.out.println("New account created: " + newAccount);
            } else if (choice == 2) {
                System.out.print("Enter account number: ");
                String accNum = scanner.nextLine();
                System.out.print("Enter amount: ");
                long amount = scanner.nextLong();
                scanner.nextLine();
                for (Account acc : accounts) {
                    if (acc != null && acc.getAccountNumber().equals(accNum)) {
                        ((Transactable) acc).deposit(amount);
                    }
                }
            } else if (choice == 3) {
                System.out.print("Enter account number: ");
                String accNum = scanner.nextLine();
                System.out.print("Enter amount: ");
                long amount = scanner.nextLong();
                scanner.nextLine();
                for (Account acc : accounts) {
                    if (acc != null && acc.getAccountNumber().equals(accNum)) {
                        ((Transactable) acc).withdraw(amount);
                    }
                }
            }
        } while (choice != 4);

        System.out.println("Thank you for using Mini Bank.");
        scanner.close();
    }
}
