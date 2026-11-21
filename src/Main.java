
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("===== ATM SYSTEM =====");
        System.out.println("1. User Login");
        System.out.println("2. Admin Login");
        System.out.print("Choose option: ");

        int option = Integer.parseInt(input.nextLine());

        // ---------------- ADMIN LOGIN ----------------
        if (option == 2) {

            System.out.print("Enter Admin ID: ");
            String adminId = input.nextLine();

            System.out.print("Enter Admin Password: ");
            String adminPass = input.nextLine();

            // Read admin credentials from environment variables
            String correctAdminId = System.getenv("ATM_ADMIN_ID");
            String correctAdminPass = System.getenv("ATM_ADMIN_PASSWORD");

            if (correctAdminId != null && correctAdminPass != null
                    && adminId.equals(correctAdminId)
                    && adminPass.equals(correctAdminPass)) {

                Admin admin = new Admin(atm);
                admin.menu();

            } else {
                System.out.println("Wrong admin credentials.");
            }

            return;
        }

        // ---------------- USER LOGIN ----------------
        System.out.print("Enter User ID: ");
        String userID = input.nextLine();

        System.out.print("Enter PIN: ");
        String pin = input.nextLine();

        User loggedUser = atm.login(userID, pin);

        if (loggedUser == null) {
            System.out.println("Login failed. Check User ID or PIN.");
            return;
        }

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, User " + userID);

        // Get user's accounts
        if (loggedUser.getAccounts().size() == 0) {
            System.out.println("No accounts available for this user.");
            return;
        }

        // Use the first account
        Account acc = loggedUser.getAccounts().get(0);

        int choice;

        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Last 5 Transactions");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            choice = Integer.parseInt(input.nextLine());

            if (choice == 1) {

                atm.balance(acc);

            } else if (choice == 2) {

                System.out.print("Enter amount: ");
                double amount = Double.parseDouble(input.nextLine());
                atm.deposit(acc, amount);

            } else if (choice == 3) {

                System.out.print("Enter amount: ");
                double amount = Double.parseDouble(input.nextLine());
                atm.withdraw(acc, amount);

            } else if (choice == 4) {

                atm.showLastNTransactions(5);

            }

        } while (choice != 0);

        atm.saveTransactions();

        System.out.println("Thank you for using the ATM!");
    }
}
