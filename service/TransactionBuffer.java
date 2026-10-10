package service;

public class TransactionBuffer {
    private final Runnable[] transactions;
    private int nextIn = 0;
    private int nextOut = 0;
    private int count = 0;
    private boolean closed = false;

    public TransactionBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be greater than zero.");
        }
        transactions = new Runnable[capacity];
    }

    public synchronized void add(Runnable transaction) throws InterruptedException {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }
        while (count == transactions.length && !closed) {
            wait();
        }
        if (closed) {
            throw new IllegalStateException("The transaction buffer is closed.");
        }

        transactions[nextIn] = transaction;
        nextIn = (nextIn + 1) % transactions.length;
        count++;
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (count == 0 && !closed) {
            wait();
        }
        if (count == 0) {
            return null;
        }

        Runnable transaction = transactions[nextOut];
        transactions[nextOut] = null;
        nextOut = (nextOut + 1) % transactions.length;
        count--;
        notifyAll();
        return transaction;
    }

    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}
