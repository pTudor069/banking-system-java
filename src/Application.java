import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

// Application Class: Entry point for the banking system
public class Application {
    private static Scanner in = new Scanner(System.in);
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Profile> profiles = new ArrayList<>();
    private static int centralAccountID = 1000;
    private static int centralUserID = 100;
    private static Profile currentUser = null;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        System.out.println("Welcome to the TP Banking System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("x. Exit");

        String choice = in.next();
        switch (choice) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            case "x":
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option, please try again.");
                menu();
        }
    }

    private static void register() {
        try {
            System.out.println("Register Account");
            System.out.print("Enter your name: ");
            String name = reader.readLine();

            while (name.isEmpty()) {
                System.out.print("Name cannot be empty. Enter your name: ");
                name = reader.readLine();
            }

            System.out.print("Enter your password: ");
            String password = reader.readLine();

            while (password.isEmpty()) {
                System.out.print("Password cannot be empty. Enter your password: ");
                password = reader.readLine();
            }
            System.out.print("Confirm your password: ");
            String confirmPassword = reader.readLine();

            int attempts = 5;
            while (!password.equals(confirmPassword) && attempts > 0) {
                attempts--;
                System.out.println("Passwords do not match. Attempts remaining: " + attempts);
                System.out.print("Confirm your password: ");
                confirmPassword = reader.readLine();
            }

            if (attempts == 0) {
                System.out.println("Registration failed. Returning to menu.");
                menu();
                return;
            }

            Profile profile = new Profile(centralUserID++, name, password);
            Account account = new Account(centralAccountID++, 0, generatePins());
            profile.addAccount(account);

            profiles.add(profile);
            accounts.add(account);

            System.out.println("Registration successful!");
            System.out.println("Profile ID: " + profile.getId());
            System.out.println("Account ID: " + account.getId());
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void login() {
        try {
            System.out.print("Enter Profile ID: ");
            System.out.println("Loh");
            int profileId = Integer.parseInt(in.next());

            System.out.print("Enter Password: ");
            String password = reader.readLine();

            for (Profile profile : profiles) {
                if (profile.getId() == profileId && profile.getPassword().equals(password)) {
                    currentUser = profile;
                    System.out.println("Login successful!");
                    bankMenu();
                    return;
                }
            }

            System.out.println("Invalid credentials. Returning to menu.");
            menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bankMenu() {
        System.out.println("Welcome, " + currentUser.getName());
        System.out.println("1. Manage Accounts");
        System.out.println("2. Edit Profile");
        System.out.println("x. Logout");

        String choice = in.next();
        switch (choice) {
            case "1":
                accountMenu();
                break;
            case "2":
                profileMenu();
                break;
            case "x":
                currentUser = null;
                menu();
                break;
            default:
                System.out.println("Invalid option, please try again.");
                bankMenu();
        }
    }

    private static void accountMenu() {
        while (true) {
            System.out.println("1. View Accounts");
            System.out.println("2. Create New Account");
            System.out.println("3. Delete Account");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("x. Back");

            String choice = in.next();
            switch (choice) {
                case "1":
                    currentUser.viewAccounts();
                    break;
                case "2":
                    Account newAccount = new Account(centralAccountID++, 0, generatePins());
                    currentUser.addAccount(newAccount);
                    accounts.add(newAccount);
                    System.out.println("Account created successfully.");
                    break;
                case "3":
                    currentUser.removeAccount(accounts);
                    break;
                case "4":
                    depositMoney();
                    break;
                case "5":
                    withdrawMoney();
                    break;
                case "x":
                    return; // Exit back to the bank menu
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void depositMoney() {
        try {
            Account account = currentUser.getAccounts().get(0);
            System.out.print("Enter amount to deposit: ");
            float amount = Float.parseFloat(in.next());
            if (amount > 0) {
                account.deposit(amount);
                System.out.println("Deposited $" + amount + " to your account.");
                System.out.println("New Balance: $" + account.getBalance());
            } else {
                System.out.println("Amount must be positive.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void withdrawMoney() {
        try {
            Account account = currentUser.getAccounts().get(0); // Default to first account
            System.out.print("Enter amount to withdraw: ");
            float amount = Float.parseFloat(in.next());
            if (amount > 0) {
                account.withdraw(amount);
                System.out.println("Withdrew $" + amount + " from your account.");
                System.out.println("New Balance: $" + account.getBalance());
            } else {
                System.out.println("Amount must be positive.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void profileMenu() {
        try {
            System.out.println("1. Change Name");
            System.out.println("2. Change Password");
            System.out.println("x. Back");

            String choice = in.next();
            switch (choice) {
                case "1":
                    System.out.print("Enter new name: ");
                    String newName = reader.readLine();
                    currentUser.setName(newName);
                    System.out.println("Name updated.");
                    break;
                case "2":
                    System.out.print("Enter new password: ");
                    String newPassword = reader.readLine();
                    currentUser.setPassword(newPassword);
                    System.out.println("Password updated.");
                    break;
                case "x":
                    bankMenu();
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    profileMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generatePins() {
        int[] pins = new int[5];
        for (int i = 0; i < pins.length; i++) {
            pins[i] = (int) (Math.random() * 9) + 1;
        }
        return pins;
    }
}
