// Transaction.java
// Stores details of each ATM transaction for history and logging.

import java.time.LocalDateTime;

public class Transaction {
    private String accountNumber;
    private String type; // deposit, withdrawal, transfer
    private double amount;
    private LocalDateTime time;

    public Transaction(String accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public String toFileString() {
        return accountNumber + "," + type + "," + amount + "," + time.toString();
    }
}
