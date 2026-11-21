// User.java
// Represents a bank user and handles authentication + lock state.

import java.util.ArrayList;

public class User {

    private String userID;
    private String pin;
    private ArrayList<Account> accounts = new ArrayList<>();

    private int failedAttempts = 0;
    private boolean locked = false;

    public User(String userID, String pin, boolean lockedStatus) {
        this.userID = userID;
        this.pin = pin;
        this.locked = lockedStatus;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
        failedAttempts = 0;
    }

    // Authentication with lock after 3 failed attempts
    public boolean authenticate(String inputPin) {
        if (locked) return false;

        if (this.pin.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;

            if (failedAttempts >= 3) {
                locked = true;
            }

            return false;
        }
    }

    // Save user info to file
    public String toFileString() {
        return userID + "," + pin + "," + locked;
    }
}
