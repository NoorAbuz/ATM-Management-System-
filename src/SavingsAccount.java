// SavingsAccount.java
// Subclass of Account.

public class SavingsAccount extends Account {
    private double interestRate; // Example: 0.02 = 2%

    public SavingsAccount(String accountNumber, String holder, double balance, double interestRate) {
        super(accountNumber, holder, balance);
        this.interestRate = interestRate;
    }

    // Method for applying interest
    public void applyInterest() {
        balance += (balance * interestRate);
    }

    @Override
    public String toFileString() {
        return "SAVINGS," + accountNumber + "," + accountHolder + "," + balance + "," + interestRate;
    }
}
