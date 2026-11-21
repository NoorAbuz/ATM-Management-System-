// Account.java
// Base class representing the bank account.

public class Account {
    protected String accountNumber;   // Unique account number
    protected String accountHolder;   // Name of the owner
    protected double balance;         // Stores account balance

    public Account(String accountNumber, String holder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = holder;
        this.balance = balance;
    }

    // Getter for balance (encapsulation)
    public double getBalance() {
        return balance;
    }

    // Deposit method (common for all account types)
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Withdraw method (polymorphic → overridden in subclasses)
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Saving account data for file storage
    public String toFileString() {
        return accountNumber + "," + accountHolder + "," + balance;
    }
}
