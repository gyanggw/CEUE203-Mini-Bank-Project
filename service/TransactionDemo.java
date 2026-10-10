package service;

import model.Account;
import model.SavingsAccount;

public class TransactionDemo {
    public static void main(String[] args) throws InterruptedException {
        Account firstAccount = new SavingsAccount("Gyan", 5000, 0);
        Account secondAccount = new SavingsAccount("Amit", 5000, 0);

        System.out.println("MiniBank transaction demo");
        runThreadPoolExample(firstAccount);
        runProducerConsumerExample(firstAccount);
        runTransferExample(firstAccount, secondAccount);

        System.out.println("\nFinal balances:");
        System.out.println(firstAccount);
        System.out.println(secondAccount);
    }

    private static void runThreadPoolExample(Account account) throws InterruptedException {
        System.out.println("\nProcessing transactions on a four-thread pool:");
        TransactionProcessor processor = new TransactionProcessor();

        for (int i = 0; i < 10; i++) {
            processor.submit(new TransactionTask(account, 100, true));
            processor.submit(new TransactionTask(account, 50, false));
        }

        processor.stop();
        System.out.println("All thread-pool transactions are complete.");
    }

    private static void runProducerConsumerExample(Account account) throws InterruptedException {
        System.out.println("\nProcessing buffered transactions:");
        TransactionBuffer buffer = new TransactionBuffer(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.add(new TransactionTask(account, 20, true));
                }
                System.out.println("Producer finished adding transactions.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                buffer.close();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Runnable transaction;
                while ((transaction = buffer.take()) != null) {
                    transaction.run();
                    System.out.println("Consumer processed a transaction.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-consumer example is complete.");
    }

    private static void runTransferExample(Account first, Account second) throws InterruptedException {
        System.out.println("\nTransferring in both directions at the same time:");
        Thread firstToSecond = new Thread(() -> transferManyTimes(first, second), "A-to-B");
        Thread secondToFirst = new Thread(() -> transferManyTimes(second, first), "B-to-A");

        firstToSecond.start();
        secondToFirst.start();
        firstToSecond.join();
        secondToFirst.join();
        System.out.println("Both transfers completed without deadlock.");
    }

    private static void transferManyTimes(Account from, Account to) {
        for (int i = 0; i < 10; i++) {
            if (!from.transferTo(to, 10)) {
                System.out.println(Thread.currentThread().getName() + " transfer failed.");
            }
        }
    }
}
