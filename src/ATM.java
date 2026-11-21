
import java.io.*;
import java.util.*;

public class ATM {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public ATM() {
        loadUsers();
        loadAccounts();
        loadTransactions();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    // ---------------- LOAD USERS ----------------
    private void loadUsers() {
        try {
            File file = new File("data/users.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] p = line.split(",");

                String id = p[0];
                String pin = p[1];
                boolean locked = Boolean.parseBoolean(p[2]);

                users.add(new User(id, pin, locked));
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    // ---------------- LOAD ACCOUNTS ----------------
    private void loadAccounts() {
        try {
            File file = new File("data/accounts.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] p = line.split(",");

                String type = p[0];
                String accNum = p[1];
                String holder = p[2];
                double bal = Double.parseDouble(p[3]);

                Account acc = null;

                if (type.equals("SAVINGS")) {
                    double rate = Double.parseDouble(p[4]);
                    acc = new SavingsAccount(accNum, holder, bal, rate);

                } else if (type.equals("CHECKING")) {
                    double limit = Double.parseDouble(p[4]);
                    acc = new CheckingAccount(accNum, holder, bal, limit);
                }

                if (acc != null) {
                    accounts.add(acc);

                    for (User u : users) {
                        if (u.getUserID().equals(holder)) {
                            u.addAccount(acc);
                        }
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    // ---------------- LOAD TRANSACTIONS ----------------
    private void loadTransactions() {
        try {
            File file = new File("data/transactions.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] p = line.split(",");

                transactions.add(
                        new Transaction(p[0], p[1],
                                Double.parseDouble(p[2]))
                );
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    // ---------------- LOGIN LOGIC ----------------
    public User login(String id, String pin) {

        for (User u : users) {

            if (u.getUserID().equals(id)) {

                if (u.isLocked()) {
                    System.out.println("This account is locked due to too many failed attempts.");
                    return null;
                }

                if (u.authenticate(pin)) {
                    saveUsers();
                    return u;

                } else {
                    System.out.println("Wrong PIN.");
                    saveUsers();
                    return null;
                }
            }
        }

        System.out.println("User not found.");
        return null;
    }

    // ---------------- SAVE USERS ----------------
    public void saveUsers() {
        try (PrintWriter pw = new PrintWriter("data/users.txt")) {

            for (User u : users) {
                pw.println(u.toFileString());
            }

        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // ---------------- ATM OPERATIONS ----------------
    public void balance(Account acc) {
        System.out.println("Current balance: " + acc.getBalance());
    }

    public void deposit(Account acc, double amount) {
        acc.deposit(amount);
        transactions.add(new Transaction(acc.accountNumber, "Deposit", amount));
    }

    public void withdraw(Account acc, double amount) {
        if (acc.withdraw(amount)) {
            transactions.add(new Transaction(acc.accountNumber, "Withdraw", amount));

        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(Account from, Account to, double amount) {
        if (from.withdraw(amount)) {
            to.deposit(amount);
            transactions.add(new Transaction(from.accountNumber, "Transfer", amount));

        } else {
            System.out.println("Transfer failed.");
        }
    }

    public void showLastNTransactions(int n) {
        int start = Math.max(0, transactions.size() - n);

        for (int i = start; i < transactions.size(); i++) {
            System.out.println(transactions.get(i));
        }
    }

    public void saveTransactions() {
        try (PrintWriter pw = new PrintWriter("data/transactions.txt")) {

            for (Transaction t : transactions) {
                pw.println(t.toFileString());
            }

        } catch (Exception e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }
}
