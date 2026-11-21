// Admin.java
// Simple admin panel for user and account management.

import java.io.*;
import java.util.*;

public class Admin {

    private ATM atm;

    public Admin(ATM atm) {
        this.atm = atm;
    }

    public void menu() {
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Create User");
            System.out.println("2. Reset User PIN");
            System.out.println("3. Unlock User Account");
            System.out.println("4. Show Total Transactions");
            System.out.println("5. Add Account");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            choice = Integer.parseInt(in.nextLine());

            if (choice == 1) createUser(in);
            if (choice == 2) resetPin(in);
            if (choice == 3) unlockUser(in);
            if (choice == 4) showReport();
            if (choice == 5) addAccount(in);

        } while (choice != 0);
    }

    private void createUser(Scanner in) {
        try {
            System.out.print("Enter new User ID: ");
            String id = in.nextLine();
            System.out.print("Enter PIN: ");
            String pin = in.nextLine();

            FileWriter fw = new FileWriter("data/users.txt", true);
            fw.write(id + "," + pin + ",false\n");
            fw.close();

            System.out.println("User created.");
        } catch (Exception e) {
            System.out.println("Error.");
        }
    }

    private void resetPin(Scanner in) {
        System.out.println("Reset PIN is optional and not fully implemented.");
    }

    private void unlockUser(Scanner in) {
        System.out.print("Enter User ID to unlock: ");
        String id = in.nextLine();

        for (User u : atm.getUsers()) {
            if (u.getUserID().equals(id)) {
                u.unlock();
                atm.saveUsers();
                System.out.println("User unlocked.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    private void showReport() {
        try {
            File file = new File("data/transactions.txt");
            Scanner sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) { sc.nextLine(); count++; }
            sc.close();
            System.out.println("Total Transactions: " + count);
        } catch (Exception e) {
            System.out.println("Error loading report.");
        }
    }

    private void addAccount(Scanner in) {
        try {
            System.out.print("Enter User ID: ");
            String id = in.nextLine();

            System.out.println("1. Checking");
            System.out.println("2. Savings");
            System.out.print("Choose type: ");
            int type = Integer.parseInt(in.nextLine());

            System.out.print("Enter new account number: ");
            String accNum = in.nextLine();

            System.out.print("Enter balance: ");
            double bal = Double.parseDouble(in.nextLine());

            FileWriter fw = new FileWriter("data/accounts.txt", true);

            if (type == 1) {
                System.out.print("Enter overdraft limit: ");
                double limit = Double.parseDouble(in.nextLine());
                fw.write("CHECKING," + accNum + "," + id + "," + bal + "," + limit + "\n");
            } else {
                System.out.print("Enter interest rate: ");
                double rate = Double.parseDouble(in.nextLine());
                fw.write("SAVINGS," + accNum + "," + id + "," + bal + "," + rate + "\n");
            }

            fw.close();
            System.out.println("Account added!");
        }
        catch (Exception e) {
            System.out.println("Error adding account.");
        }
    }
}
