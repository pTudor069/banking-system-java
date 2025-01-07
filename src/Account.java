class Account {
    private int id;
    private float balance;
    private int[] pin;

    public Account(int id, float balance, int[] pin) {
        this.id = id;
        this.balance = balance;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float amount) {
        balance += amount;
    }public void withdraw(float amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    @Override
    public String toString() {
        return "Account ID: " + id + ", Balance: $" + balance;
    }
}