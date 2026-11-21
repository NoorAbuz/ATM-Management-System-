// CheckingAccount.java
// Extends Account with overdraft protection.

public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String accNum, String holder, double balance, double overdraftLimit) {
        super(accNum, holder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    // Overdraft allowed
    @Override
    public boolean withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // For saving into accounts.txt
    @Override
    public String toFileString() {
        return "CHECKING," + accountNumber + "," + accountHolder + "," + balance + "," + overdraftLimit;
    }
}
