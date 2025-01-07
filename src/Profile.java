import java.util.ArrayList;

class Profile {
    private int id;
    private String name;
    private String password;
    private ArrayList<Account> accounts;

    public Profile(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Account> getAccounts() { // Add this method
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(ArrayList<Account> allAccounts) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts to remove.");
            return;
        }

        Account accountToRemove = accounts.get(0); // Placeholder for account selection logic
        accounts.remove(accountToRemove);
        allAccounts.remove(accountToRemove);
        System.out.println("Account removed.");
    }

    public void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        for (Account account : accounts) {
            System.out.println(account);
        }
        System.out.println(); // Ensure proper spacing after displaying accounts
    }
}
